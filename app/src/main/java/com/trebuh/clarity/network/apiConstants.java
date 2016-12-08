package com.trebuh.clarity.network;

public class ApiConstants {

    public static final String ENDPOINT = "https://api.500px.com/v1/";
    public static final String ENDPOINT_PHOTOS = "photos";
    public static final String ENDPOINT_SEARCH = "search";
    public static final String CONSUMER_KEY = "lp21ZfxrVCcoYDSWsxVo5f40jO8x5AsGU8RntF5f";

    public static final String PARAM_CONSUMER_KEY = "consumer_key";
    public static final String PARAM_IMAGE_SIZE = "image_size";
    public static final String PARAM_SEARCH_TERM = "term";

    public static final String PARAM_FEATURE = "feature";
    public static final String PARAM_SORT_METHOD = "sort";

    // Params that are not required by the 500px API
    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_TAGS = "tags";
    public static final String EXTRA_ONLY_CATEGORY = "only";
    public static final String EXTRA_PAGE = "page";
    public static final String EXTRA_RESULTS_PER_PAGE = "rpp";

    // Default values:
    public static final String DEFAULT_IMAGE_SIZE = "3";
    public static final String DEFAULT_FEATURE = "fresh_today";
    public static final String DEFAULT_SORT_METHOD = "created_at";
    public static final int DEFAULT_RESULTS_PER_PAGE = 30;

    // Response keys:
    public static final String KEY_TOTAL_PAGES = "total_pages";
    public static final String KEY_TOTAL_ITEMS = "total_items";
    public static final String KEY_PHOTOS = "photos";

    // [photos]
    public static final String KEY_PHOTO_ID = "id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_PHOTO_NAME = "name";
    public static final String KEY_PHOTO_DESCRIPTION = "description";
    public static final String KEY_PHOTO_CREATED_AT = "created_at";
    public static final String KEY_PHOTO_RATING = "rating";
    public static final String KEY_PHOTO_TIMES_VIEWED = "times_viewed";
    public static final String KEY_PHOTO_CATEGORY_NUM = "category";
    public static final String KEY_PHOTO_WIDTH = "width";
    public static final String KEY_PHOTO_HEIGHT = "height";
    public static final String KEY_PHOTO_VOTE_COUNT = "votes_count";
    public static final String KEY_PHOTO_FAV_COUNT = "favorites_count";
    public static final String KEY_PHOTO_COMMENT_COUNT = "comments_count";
    public static final String KEY_PHOTO_IS_NSFW = "nsfw";
    public static final String KEY_PHOTO_URL = "image_url";
    public static final String KEY_USER = "user";

    // [photos.user]
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_FIRSTNAME = "firstname";
    public static final String KEY_USER_LASTNAME = "lastname";
    public static final String KEY_USER_CITY = "lastname";
    public static final String KEY_USER_COUNTRY = "lastname";
    public static final String KEY_USER_PIC_URL = "userpic_https_url";
    public static final String KEY_USER_FOLLOWER_COUNT = "followers_count";
    public static final String KEY_USER_AVATARS = "avatars";
    public static final String KEY_AVATAR_DEFAULT = "default";
    public static final String KEY_HTTPS = "https";

    public static final String FEATURE_DEFAULT = "";
    public static final String FEATURE_POPULAR = "popular";
    public static final String FEATURE_HIGHEST_RATED = "highest_rated";
    public static final String FEATURE_UPCOMING = "upcoming";
    public static final String FEATURE_EDITORS = "editors";
    public static final String FEATURE_FRESH_TODAY = "fresh_today";
    public static final String FEATURE_FRESH_YESTERDAY = "fresh_yesterday";
    public static final String FEATURE_FRESH_WEEK = "fresh_week";

    // requires user_id or username:
    public static final String FEATURE_USER = "user";
    public static final String FEATURE_USER_FRIENDS = "user_friends";
    public static final String FEATURE_USER_FAVORITES = "user_favorites";

    public static final String SORT_METHOD_DEFAULT = "";
    public static final String SORT_METHOD_CREATED_AT = "created_at";
    public static final String SORT_METHOD_RATING = "rating";
    public static final String SORT_METHOD_TIMES_VIEWED = "times_viewed";
    public static final String SORT_METHOD_VOTES_COUNT = "votes_count";
    public static final String SORT_METHOD_FAVORITES_COUNT = "favorites_count";
    public static final String SORT_METHOD_COMMENTS_COUNT = "comments_count";

    // search
    public static final String NO_SEARCH_QUERY = "no_search_query";
    public static final int FIRST_PAGE = 1;
    public static final String NO_FEATURE = "no_sort_method";
    public static final String NO_SORT_METHOD = "no_sort_method";

}
