package org.geekhub.studentsregistry.web.users;

public enum Role {
    USER, ADMIN;

    public static Role from(String inputRole) {
        for (var role : Role.values()) {
            if (role.name().equals(inputRole)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Incorrect user Role.");
    }

}
