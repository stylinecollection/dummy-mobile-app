package com.avijitbabu.dummy_mobile_app.Variables;

import android.support.v4.app.Fragment;



public class Vars {

    public static final String SECURITY_CODE = "AVIJIT";
    public static final String PASSWORD_SALT = "AdjkasjdNASASLDJKASDJj";

    public static final String PREFS_NAME = "CourierServiceAppLocalDB";
    public static final String PREFS_IS_USER_ADMIN = "IsUserIsAdmin";
    public static final String PREFS_DEVICE_UNIQUE_ID = "DeviceId";

    public static final String APPTAG = "CSAppTag";

    public static Fragment currentFragment;
    public static boolean isUserAdmin;

    public static void reset() {
        currentFragment = null;
        isUserAdmin = false;
    }

    public static class Transporter {
        // Admin Employee Works
        public static final String ARGS_ADMIN_EMPLOYEE_ID = "CLIENTID";

        // Admin Profile
        public static final String ARGS_ADMIN_PROFILE_NAME = "PROFILE_NAME";
        public static final String ARGS_ADMIN_PROFILE_PHONE = "PROFILE_NAME";
    }
}

