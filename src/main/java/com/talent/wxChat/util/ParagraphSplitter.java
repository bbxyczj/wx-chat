package com.talent.wxChat.util;

import java.util.ArrayList;
import java.util.List;

public class ParagraphSplitter {
    public static List<String> reorganizeIntoParagraphs(String article, int maxParagraphLength) {
        List<String> paragraphs = new ArrayList<>();

        if (article == null || article.isEmpty()) {
            return paragraphs;
        }

        // 根据换行符拆分文章为段落
        String[] lines = article.split("\\n");

        StringBuilder paragraphBuilder = new StringBuilder();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                // 当遇到空行时，将当前段落添加到列表中，并重置段落构建器
                if (paragraphBuilder.length() > 0) {
                    paragraphs.add(paragraphBuilder.toString());
                    paragraphBuilder.setLength(0);
                }
            } else {
                // 将当前行添加到段落构建器中，并检查段落长度
                paragraphBuilder.append(line).append("\n");

                // 如果段落长度超过最大长度，则将当前段落添加到列表中，并重置段落构建器
                if (paragraphBuilder.length() >= maxParagraphLength) {
                    paragraphs.add(paragraphBuilder.toString());
                    paragraphBuilder.setLength(0);
                }
            }
        }

        // 添加最后一个段落
        if (paragraphBuilder.length() > 0) {
            paragraphs.add(paragraphBuilder.toString());
        }

        // 重新组合段落为大段落
        List<String> reorganizedParagraphs = new ArrayList<>();
        StringBuilder reorganizedParagraphBuilder = new StringBuilder();

        for (String paragraph : paragraphs) {
            if (reorganizedParagraphBuilder.length() + paragraph.length() <= maxParagraphLength) {
                // 将当前段落添加到大段落构建器中
                reorganizedParagraphBuilder.append(paragraph);
            } else {
                // 当大段落长度超过最大长度时，将当前大段落添加到列表中，并重置大段落构建器
                reorganizedParagraphs.add(reorganizedParagraphBuilder.toString());
                reorganizedParagraphBuilder.setLength(0);

                // 将当前段落添加到新的大段落构建器中
                reorganizedParagraphBuilder.append(paragraph);
            }
        }

        // 添加最后一个大段落
        if (reorganizedParagraphBuilder.length() > 0) {
            reorganizedParagraphs.add(reorganizedParagraphBuilder.toString());
        }

        return reorganizedParagraphs;
    }


    public static void main(String[] args) {
        String article = "敬爱的老师，您是明灯，\n" +
                "照亮我前进的方向。\n" +
                "您用耐心和智慧，\n" +
                "引导我走向知识的海洋。\n" +
                "\n" +
                "您的言传身教，\n" +
                "让我充满了信心。\n" +
                "您无私的奉献，\n" +
                "让我感受到真情。\n" +
                "\n" +
                "您不仅是我的导师，\n" +
                "更是我心灵的引导者。\n" +
                "您的教诲如春风拂面，\n" +
                "温暖了我成长的道路。\n" +
                "\n" +
                "您用知识点亮了我的梦，\n" +
                "催我奋发向前飞翔。\n" +
                "在大自然的课堂上，\n" +
                "您让我懂得了生命的价值。\n" +
                "\n" +
                "谢谢您给予的关怀和鼓励，\n" +
                "是您让我勇敢向前。\n" +
                "无论我走到哪里，\n" +
                "您的教诲都永远铭刻在心间。\n" +
                "\n" +
                "老师，您是我生命的引擎，\n" +
                "是我坚定前行的力量。\n" +
                "今天我向您致以深深的敬意，\n" +
                "永远怀抱着对您的感激之情。";
        List<String> paragraphs = reorganizeIntoParagraphs(article, 500);

        for (String paragraph : paragraphs) {
            System.out.println(paragraph);
            System.out.println("---------------------");
        }
    }
}
