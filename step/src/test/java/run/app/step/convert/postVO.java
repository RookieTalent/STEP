package run.app.step.convert;

/**
 * @author lingSong
 * @date 2020/8/16 20:36
 */
public class postVO extends postParam {


    public static void main(String[] args) {
        postVO postVO = new postVO();
        Class<?> superclass = postVO.getClass().getSuperclass();
        System.out.println(superclass);
    }
}
