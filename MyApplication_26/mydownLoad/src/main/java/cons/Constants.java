package cons;

/**
 * 公共常量类
 * Created by Administrator on 2015/11/3.
 */
public final class Constants {

    /**任务最大下载量*/
    public final static int TASK_NUM_MAX = 4;

    /**每个任务的下载线程数*/
    public final static int THREAD_NUM = 3;

    /**
     * 文件访问模式
     */
    public static final class AccessModes{
        public static final String ACCESS_MODE_R = "r";
        public static final String ACCESS_MODE_RW = "rw";
        public static final String ACCESS_MODE_RWS = "rws";
        public static final String ACCESS_MODE_RWD = "rwd";
    }

    /**
     * 数据库常量
     */
    public static final class DBCons{
        /**唯一ID*/
        public static final String _ID = "_id";

        /** 下载任务表名 */
        public static final String TB_TASK = "task_info";
        /** 下载文件的url */
        public static final String TB_TASK_URL_REAL = "real_url";
        /** 文件存储位置 */
        public static final String TB_TASK_FILE_PATH = "file_path";
        /** 文件名称 */
        public static final String TB_TASK_FILE_NAME = "file_name";
        /** 当前文件的进度 */
        public static final String TB_TASK_CURRENT_LENGTH = "current_length";
        /** 文件的大小 */
        public static final String TB_TASK_FILE_LENGTH = "file_length";

        /** 下载线程表名 */
        public static final String TB_THREAD = "thread_info";
        /** 下载文件的url */
        public static final String TB_THREAD_URL_REAL = "real_url";
        /** 文件存储位置 */
        public static final String TB_THREAD_FILE_PATH = "file_path";
        /** 开始下载的节点 */
        public static final String TB_THREAD_START = "start";
        /** 结束下载的节点 */
        public static final String TB_THREAD_END = "end";
        /** 线程Id */
        public static final String TB_THREAD_ID = "id";

        /** 创建下载任务表的sql语句 */
        public static final String TB_TASK_SQL_CREATE = "CREATE TABLE " +
                DBCons.TB_TASK + "(" +
                DBCons._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBCons.TB_TASK_URL_REAL + " CHAR, " +
                DBCons.TB_TASK_FILE_PATH + " CHAR, " +
                DBCons.TB_TASK_FILE_NAME + " CHAR, " +
                DBCons.TB_TASK_CURRENT_LENGTH + " INTEGER, " +
                DBCons.TB_TASK_FILE_LENGTH + " INTEGER)";
        /** 升级下载任务表的sql语句 */
        public static final String TB_TASK_SQL_UPGRADE = "DROP TABLE IF EXISTS " +
                DBCons.TB_TASK;

        /** 创建下载线程表的sql语句 */
        public static final String TB_THREAD_SQL_CREATE = "CREATE TABLE " +
                DBCons.TB_THREAD + "(" +
                DBCons._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBCons.TB_THREAD_URL_REAL + " CHAR, " +
                DBCons.TB_THREAD_FILE_PATH + " CHAR, " +
                DBCons.TB_THREAD_START + " INTEGER, " +
                DBCons.TB_THREAD_END + " INTEGER, " +
                DBCons.TB_THREAD_ID + " CHAR)";

        /** 升级下载线程表的sql语句 */
        public static final String TB_THREAD_SQL_UPGRADE = "DROP TABLE IF EXISTS " +
                DBCons.TB_THREAD;

        /** 删除下载任务表所有数据的sql语句 */
        public static final String TB_TASK_SQL_DELETE_ALL = "TRUNCATE TABLE " +
                DBCons.TB_TASK;

        /** 删除下载线程表所有数据的sql语句 */
        public static final String TB_THREAD_SQL_DELETE_ALL = "TRUNCATE TABLE " +
                DBCons.TB_THREAD;
    }

}
