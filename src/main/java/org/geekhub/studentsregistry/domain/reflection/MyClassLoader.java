package org.geekhub.studentsregistry.domain.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MyClassLoader extends ClassLoader {

    private final Map<String, Class<?>> classesHash = new HashMap<>();
    private final String[] classPath;

    public MyClassLoader(String[] classPath) {
        this.classPath = classPath;
    }

    @Override
    public String getName() {
        return "MyClassLoader";
    }

    protected synchronized Class<?> loadClass(String inputName, boolean resolve) throws ClassNotFoundException {

        String name = Optional.ofNullable(inputName)
                .orElseThrow(() -> new IllegalArgumentException("Class name is null"));

        Class<?> result = findClass(name);
        if (resolve)
            resolveClass(result);
        return result;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> result = classesHash.get(name);

        if (result != null) {
            System.out.println(">>>>> % Class " + name + " found in cache");
            return result;
        }

        File f = findFile(name.replace('.', '/'), ".class");
        if (f == null) return findSystemClass(name);

        try {
            byte[] classBytes = loadFileAsBytes(f);
            result = defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Cannot load class " + name + ": " + e);
        } catch (ClassFormatError e) {
            throw new ClassNotFoundException("Format of class file incorrect for class " + name + " : " + e);
        }
        classesHash.put(name, result);
        return result;
    }

    protected java.net.URL findResource(String name) {
        File f = findFile(name, "");
        if (f == null) return null;
        try {
            return f.toURI().toURL();
        } catch (java.net.MalformedURLException e) {
            return null;
        }
    }

    private File findFile(String name, String extension) {
        File f;
        for (String s : classPath) {
            f = new File((new File(s)).getPath() + File.separatorChar + name.replace('/',
                    File.separatorChar) + extension);
            if (f.exists())
                return f;
        }
        return null;
    }

    private static byte[] loadFileAsBytes(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            f.read(result, 0, result.length);
        }
        return result;
    }

}
