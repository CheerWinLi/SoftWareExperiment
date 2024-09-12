package lictory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author :Lictory
 * @date : 2024/09/12
 */
public class Solution {

    private static final int SIZE = 128;

    private static final int LENGTH = 3;

    public static void main(String[] args) {
        // 检查命令行参数
        if (args.length != LENGTH) {
            System.err.println("请正确输入参数: java  <原文文件路径> <抄袭版论文文件路径> <输出结果文件路径>");
            return;
        }

        // 源文件 D:\IDEA\GitHubProject\SoftWareExperiment\3122004440\src\main\resources\orig.txt
        String originalFilePath = args[0];

        String plagiarizedFilePath = args[1];

        //默认的 输出文件 D:\IDEA\GitHubProject\SoftWareExperiment\3122004440\src\main\resources\result.txt
        String outputFilePath = args[2];
        try {
            //读出文件信息
            String originalContent = new String(Files.readAllBytes(Paths.get(originalFilePath)));
            String plagiarizedContent = new String(Files.readAllBytes(Paths.get(plagiarizedFilePath)));

            long simHash1 = computeSimHash(originalContent);
            long simHash2 = computeSimHash(plagiarizedContent);

            if (simHash1 == 0 && simHash2 == 0) {
                System.out.println("两个文本都没有有效内容，无法计算相似度。");
                return;
            }

            double similarity = computeSimilarity(simHash1, simHash2);
            writeOutputToFile(outputFilePath, similarity);
            System.out.println("相似度计算完成，结果已写入文件: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    /**
     * 计算文本的 SimHash
     * @param text 文本内容
     * @return 文本的 SimHash 值
     */
    private static long computeSimHash(String text) {
        // SimHash 的维度
        int[] v = new int[128];

        // 逐个汉字处理
        for (char c : text.toCharArray()) {
            // 每个汉字的权重可以根据需要调整
            int weight = 1;
            // 获取汉字的哈希值
            long hash = String.valueOf(c).hashCode();

            for (int i = 0; i < SIZE; i++) {
                if ((hash & (1L << i)) != 0) {
                    // 正向加权
                    v[i] += weight;
                } else {
                    // 反向加权
                    v[i] -= weight;
                }
            }
        }

        long simHash = 0;
        for (int i = 0; i < SIZE; i++) {
            if (v[i] > 0) {
                simHash |= (1L << i);
            }
        }
        return simHash;
    }

    private static double computeSimilarity(long simHash1, long simHash2) {
        // 汉明距离
        int hammingDistance = Long.bitCount(simHash1 ^ simHash2);
        // 归一化为 [0, 1]
        return 1.0 - (double) hammingDistance / 128;
    }

    private static void writeOutputToFile(String filePath, double similarity) throws IOException {
        String output = "计算出的相似度: " + similarity;
        Files.write(Paths.get(filePath), output.getBytes());
    }
}
