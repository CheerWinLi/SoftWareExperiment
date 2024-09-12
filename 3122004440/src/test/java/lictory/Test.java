package lictory;

/**
 * @author :Lictory
 * @date : 2024/09/12
 */

public class Test {

    private String originTextPath = "D:\\IDEA\\GitHubProject\\SoftWareExperiment\\3122004440\\src\\main\\resources\\orig.txt";
    private String resultPath = "D:\\IDEA\\GitHubProject\\SoftWareExperiment\\3122004440\\src\\main\\resources\\result.txt";

    //参数不足情况
    @org.junit.jupiter.api.Test
    public void testArgs() {
        Solution.main(new String[]{"a", "b"});
    }

    //空文本测试
    @org.junit.jupiter.api.Test
    public void testNullText() {
        String emptyPath = "D:\\IDEA\\GitHubProject\\SoftWareExperiment\\3122004440\\src\\main\\resources\\empty.txt";
        Solution.main(new String[]{originTextPath, emptyPath, resultPath});
    }
}
