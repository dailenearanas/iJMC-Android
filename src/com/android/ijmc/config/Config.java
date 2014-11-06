package com.android.ijmc.config;

import java.util.ArrayList;

import android.os.Environment;

public class Config {

    public Config() {
        
    }
    
    public static String HOST_NAME = "http://192.168.43.74";
    
    public static String FOLDER_NAME = "ijmc-temp";

    public static String BASE_URL = HOST_NAME + "/" + FOLDER_NAME + "/public";

    public static String JSON_URL = HOST_NAME + "/" + FOLDER_NAME + "/public/jsonlisting";
    
    public static String JSON_REQUEST_URL = HOST_NAME + "/" + FOLDER_NAME + "/public/jsonrequest";
    
    public static String IMAGE_FACULTY_BASE_URL = HOST_NAME + "/" + FOLDER_NAME + "/public/faculty_image";
    
    public static String IMAGE_STUDENT_BASE_URL = HOST_NAME + "/" + FOLDER_NAME + "/public/student_img";
    
    public static String MUSIC_BASE_URL = HOST_NAME + "/" + FOLDER_NAME + "/public/music_files";
    
    public static String EXTERNAL_FOLDER = Environment.getExternalStorageDirectory() + "/.ijmc";
    
    public static String MUSIC_FILENAME = "Luther_Vandross_The_Closer_I_Get_To_You.mp3";
    

    
    public static String FACULTY_TABLE = "faculties";

    public static String STUDENT_TABLE = "students";

    public static String CONTENT_TABLE = "contents";

    public static String DEPARTMENT_TABLE = "departments";
    
    public static String SEAL_TABLE = "jmcseals";
    
    public static String COURSE_TABLE = "courses";
    
    public static String POSITION_TABLE = "positions";
    
    public static String SSG_TABLE = "ssgofficers";
    
    public static String MUSIC_TABLE = "musics";
    
    public static String OTHER_TABLE = "others";
    
    
    //JSON LISTINGS 
    public static String CONTENT_JSON = "contentlist.json";

    public static String DEPARTMENT_JSON = "departmentlist.json";

    public static String STUDENT_JSON = "studentlist.json";
    
    public static String COURSE_JSON = "courselist.json";
    
    public static String FACULTY_JSON = "facultylist.json";
    
    public static String POSITION_JSON = "positionlist.json";
    
    public static String SSG_JSON = "ssglist.json";
    
    public static String SEALS_JSON = "jmcseallist.json";
    
    public static String MUSIC_JSON = "musiclist.json";
    
    public static String OTHER_JSON = "otherlist.json";
    
    
    
    public static String STUDENT_CONFIRM_JSON = "studentverify.json";
    
    public static String FACULTY_CONFIRM_JSON = "facultyverify.json";

    public String jmcProfile;

    public String LOGIN = "LOGIN";

    
    //SHARED PREFERENCE FILE
    public static String SHA_NAME = "IJMC_SETTING";
    
    
    //SHARED PREFERENCE FIELDS
    public static String SHA_USR_ID = "USR_ID";

    public static String SHA_USR_FNAME = "USR_FNAME";

    public static String SHA_USR_MNAME = "USR_MNAME";

    public static String SHA_USR_LNAME = "USR_LNAME";
    
    public static String SHA_USR_SUFFIX = "USR_SUFFIX";

    public static String SHA_USR_DEPT_ID = "USR_DEPT_ID";
    
    public static String SHA_USR_POS_ID = "USR_POS_ID";
    
    public static String SHA_USR_IMAGE_FILE = "USR_IMAGE_FILE";
    
    public static String SHA_USR_TYPE = "USR_TYPE";
    
    public static String SHA_LOGGED_IN = "USR_LOGIN_FLAG";

    
  //------START----------------JSON AND DATABASE TAGS---------------------------
    
  //CONTENT TAGS
    public static final String TAG_CONTENT_ID = "id";

    public static final String TAG_CONTENT_TYPE = "content_type";

    public static final String TAG_CONTENT_BODY = "content_body";

    
  //FACULTY TAGS
    public static final String TAG_FACULTY_ID = "fc_idn";

    public static final String TAG_FACULTY_FNAME = "fc_name";

    public static final String TAG_FACULTY_MNAME = "fc_mname";

    public static final String TAG_FACULTY_LNAME = "fc_lname";

    public static final String TAG_FACULTY_SFX = "fc_suffix";

    public static final String TAG_FACULTY_GENDER = "fc_gender";

    public static final String TAG_FACULTY_IMAGE = "image_path";

    public static final String TAG_FACULTY_DEPT = "dept_id";

    public static final String TAG_FACULTY_POSITION = "pos";

    
  //DEPARTMENT TAGS
    public static final String TAG_DEPT_ID = "id";

    public static final String TAG_DEPT_TITLE = "dept_title";

    public static final String TAG_DEPT_DESC = "dept_desc";
    
   //COURSE TAGS
    public static final String TAG_COURSE_ID = "id";
    
    public static final String TAG_COURSE_TITLE = "crs_title";
    
    public static final String TAG_COURSE_DESC = "crs_desc";
    
    public static final String TAG_COURSE_DEPT_ID = "dept_id";
    
    //POSITION TAGS
    public static final String TAG_POSITION_ID = "id";
    
    public static final String TAG_POSITION_TITLE = "pos_title";
    
    //SSG OFFICER TAGS
    public static final String TAG_SSG_FNAME = "firstname";
    
    public static final String TAG_SSG_MNAME = "middlename";
    
    public static final String TAG_SSG_LNAME = "lastname";
    
    public static final String TAG_SSG_IMAGE = "img_path";
    
    public static final String TAG_SSG_LEVEL = "level";
    
    public static final String TAG_SSG_CRS_ID = "crs_id";
    
    public static final String TAG_SSG_POS_ID = "pos_id";
    
    //SEAL TAGS
    public static final String TAG_SEAL_IMAGE = "img_path";
    
    public static final String TAG_SEAL_NAME = "js_name";
    
    public static final String TAG_SEAL_DESC = "js_desc";
    
    //MUSIC TAGS
    public static final String TAG_MUSIC_NAME = "music_name";
    
    public static final String TAG_MUSIC_PATH = "music_path";
    
    //OTHER TAGS
    public static final String TAG_OTHER_TITLE = "o_title";
    
    public static final String TAG_OTHER_BODY = "o_body";
    
    public static final String TAG_OTHER_USR_ID = "user_id";
    
    
  //------END------------------JSON AND DATABASE TAGS---------------------------

    
  //---------------METHODS-------------- 
    public static ArrayList<String> getJsonUrls()
    {
        ArrayList<String> jsonUrls = new ArrayList<String>();
        jsonUrls.add(JSON_URL + "/" + CONTENT_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + DEPARTMENT_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + STUDENT_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + COURSE_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + FACULTY_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + POSITION_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + SSG_JSON);//with model
        jsonUrls.add(JSON_URL + "/" + SEALS_JSON);//with model
        return jsonUrls;
    }
}
