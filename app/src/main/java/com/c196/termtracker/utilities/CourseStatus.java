package com.c196.termtracker.utilities;

public enum CourseStatus {
    IN_PROGRESS {
        @Override
        public String toString() {
            return "In Progress";
        }
    },

    COMPLETED {
        @Override
        public String toString() {
            return "Completed";
        }
    },

    DROPPED {
        @Override
        public String toString() {
            return "Dropped";
        }
    },

    PLAN_TO_TAKE {
        @Override
        public String toString() {
            return "Plan to Take";
        }
    }
}
