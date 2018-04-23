package com.kolon.comlife.admin.imageStore.model;

import java.util.HashMap;
import java.util.Map;

public class ImageInfoUtil {
    /**
     * 아래의 SIZE_SUFFIX_??? 및 IMAGE_TYPE_???의 값은 lambda-image-resizer의 설정값과 동일하게 유지해야합니다.
     * 설정값(ThumnailSizes)은 /services/lambda-image-resizer/src/index.js 에서 찾을 수 있습니다.
     */
    public final static String SIZE_SUFFIX_TINY   = "/t";
    public final static String SIZE_SUFFIX_SMALL  = "/s";
    public final static String SIZE_SUFFIX_MEDIUM = "/m";
    public final static String SIZE_SUFFIX_LARGE  = "/l";

    public final static String IMAGE_TYPE_ARTICLE = "FEED"; // Feed, Event, Notice
    public final static String IMAGE_TYPE_PROFILE = "PROFILE"; // User 프로필 사진
    public final static String IMAGE_TYPE_ADMINPROFILE = "ADMINPROFILE"; // Admin 프로필 사진
    public final static String IMAGE_TYPE_TICKET  = "TICKET";  // 지원 접수시, 업로드하는 이미지
    public final static String IMAGE_TYPE_COMPLEX = "COMPLEX"; // 현장 대표(로고) 이미지 업로드
    public final static String IMAGE_TYPE_RESV    = "RESV"; // 예약 관련 이미지
    public final static String IMAGE_TYPE_GUIDE    = "GUIDE"; // Info > Guide 관련 이미지
    public final static String IMAGE_TYPE_BENEFITS    = "BENEFITS"; // Info > Benefit 관련 이미지
    public final static String IMAGE_TYPE_BENEFITS_LOGO    = "BENEFITSLOGO"; // Info > Benefit 표시 로고 이미지

    private static Map<String, String> supportedImageTypes = null;

    private static String IMAGE_STORE_BASE_PATH = "/imageStore";

    private static String getImageTypePathInternal(String imageType) {
        return imageType.toLowerCase();
    }

    public static String getImageTypePath(String imageType) {

        return getImageTypePathInternal(imageType) + "/";
    }


    public static String getImagePath( int imageIdx ) {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append( IMAGE_STORE_BASE_PATH );
        strBuilder.append( "/" );
        strBuilder.append( imageIdx );

        return strBuilder.toString();
    }


    public static String getImagePath( String sizeSuffix, int imageIdx ) {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append( IMAGE_STORE_BASE_PATH );
        strBuilder.append( "/" );
        strBuilder.append( imageIdx );
        strBuilder.append( sizeSuffix );

        return strBuilder.toString();
    }

    public static boolean isSupportedType( String imageType ) {
        if(supportedImageTypes == null ) {
            synchronized( ImageInfoUtil.class ) {
                supportedImageTypes = new HashMap<>();
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_ARTICLE, "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_PROFILE, "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_ADMINPROFILE, "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_TICKET,  "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_COMPLEX, "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_RESV,    "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_GUIDE,    "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_BENEFITS,    "Y");
                supportedImageTypes.put(ImageInfoUtil.IMAGE_TYPE_BENEFITS_LOGO,    "Y");

            }
        }

        return ( supportedImageTypes.get(imageType.toUpperCase()) != null &&
                 "Y".equals( supportedImageTypes.get(imageType.toUpperCase())) );
    }
}
