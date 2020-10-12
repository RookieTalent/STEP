package run.app.step.common.enums.code;

import javax.ws.rs.DELETE;

/**
 * 操作类型
 *
 * @author lingSong
 * @date 2020/10/9 21:31
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 列表
     */
    LIST,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 清空数据
     */
    CLEAN,
}
