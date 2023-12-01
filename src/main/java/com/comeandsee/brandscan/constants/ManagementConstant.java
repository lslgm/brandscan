package com.comeandsee.brandscan.constants;

public class ManagementConstant {
    /*
        URLs
        - Brand
        - Brand request
        - Member
    */
    public final static String MANAGEMENT_BASE_URL = "/management";

    // Brand
    public final static String MANAGEMENT_BRAND_URL = "brand";
    public final static String MANAGEMENT_BRAND_LIST_URL = MANAGEMENT_BRAND_URL + "/list";
    public final static String MANAGEMENT_BRAND_REGISTER_URL = MANAGEMENT_BRAND_URL + "/register";
    public final static String MANAGEMENT_BRAND_DETAIL_URL = MANAGEMENT_BRAND_URL + "/detail";

    // Brand request
    public final static String MANAGEMENT_BRAND_REQUEST_URL = "brand_request";
    public final static String MANAGEMENT_BRAND_REQUEST_LIST_URL = MANAGEMENT_BRAND_REQUEST_URL + "/list";
    public final static String MANAGEMENT_BRAND_REQUEST_DETAIL_URL = MANAGEMENT_BRAND_REQUEST_URL + "/detail";

    // Member
    public final static String MANAGEMENT_MEMBER_URL = "member";
    public final static String MANAGEMENT_MEMBER_LIST_URL = MANAGEMENT_MEMBER_URL + "/list";

    /*
        Views
        - Brand
        - Brand request
        - Member
     */
    public final static String MANAGEMENT_BASE_VIEW = "contents/management";

    // Brand
    public final static String MANAGEMENT_BRAND_VIEW = MANAGEMENT_BASE_VIEW + "/brand";
    public final static String MANAGEMENT_BRAND_LIST_VIEW = MANAGEMENT_BRAND_VIEW + "/list";
    public final static String MANAGEMENT_BRAND_REGISTER_VIEW = MANAGEMENT_BRAND_VIEW + "/register";
    public final static String MANAGEMENT_BRAND_DETAIL_VIEW = MANAGEMENT_BRAND_VIEW + "/detail";

    // Brand request
    public final static String MANAGEMENT_BRAND_REQUEST_VIEW = MANAGEMENT_BASE_VIEW + "/brand_request";
    public final static String MANAGEMENT_BRAND_REQUEST_LIST_VIEW = MANAGEMENT_BRAND_REQUEST_VIEW + "/list";
    public final static String MANAGEMENT_BRAND_REQUEST_DETAIL_VIEW = MANAGEMENT_BRAND_REQUEST_VIEW + "/detail";

    // Member
    public final static String MANAGEMENT_MEMBER_VIEW = MANAGEMENT_BASE_VIEW + "/member";
    public final static String MANAGEMENT_MEMBER_LIST_VIEW = MANAGEMENT_MEMBER_VIEW + "/list";

    /* ETC */
    // Table name
    public final static String MEMBER_TABLE_NAME = "member_tbl";
    public final static String BRAND_TABLE_NAME = "brand_tbl";
    public final static String BRAND_REQUEST_TABLE_NAME = "brand_request_tbl";
    public final static String BRAND_RETOUCH_TABLE_NAME = "brand_qna_tbl";

    // Validation message
    public final static String BRAND_NAME_VALIDATION_MESSAGE = "브랜드명은 필수 입력 값입니다.";

    // Response message
    public final static String BRAND_UPDATE_SUCCESS_MESSAGE = "성공적으로 수정 하였습니다.";

    // Error message
    public final static String NOT_FOUND_TYPE_ERROR_MESSAGE = "존재 하지 않는 유형 입니다.";
    public final static String NOT_FOUND_PARAMETER_ERROR_MESSAGE = "입력 값이 존재 하지 않습니다.";
}
