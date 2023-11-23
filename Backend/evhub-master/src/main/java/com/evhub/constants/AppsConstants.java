package com.evhub.constants;

import org.apache.commons.lang3.StringUtils;

public class AppsConstants {

    public enum ResponseStatus {
        SUCCESS, FAILED;

        public static ResponseStatus resolveStatus(String statusStr) {
            ResponseStatus matchingStatus = null;
            if (!StringUtils.isEmpty(statusStr)) {
                matchingStatus = ResponseStatus.valueOf(statusStr.trim());
            }
            return matchingStatus;
        }
    }

    public enum UserRole {
        ADMIN("Admin"),
        VENDOR("Vendor"),
        OWNER("Owner"),
        USER("User");

        private String description;

        UserRole(String description) {
            this.description = description;
        }

        public static UserRole resolveUserRole(String userRoleStr) {
            UserRole matchingUserRole = null;
            if (StringUtils.isNotEmpty(userRoleStr)) {
                matchingUserRole = UserRole.valueOf(userRoleStr.trim());
            }
            return matchingUserRole;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Status {
        ACTIVE("Active"), INACTIVE("Inactive");

        private final String description;

        Status(String description) {
            this.description = description;
        }

        public static Status resolveStatus(String str) {
            Status matchingStr = null;
            if (!StringUtils.isEmpty(str)) {
                matchingStr = Status.valueOf(str.trim());
            }
            return matchingStr;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PaymentMethod {
        CASH, CARD
    }

    public enum ChargingType {
        NORMAL,
        HIGH_VOLTAGE
    }
}
