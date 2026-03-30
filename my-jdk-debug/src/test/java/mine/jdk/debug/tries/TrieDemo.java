package mine.jdk.debug.tries;

import java.util.*;

/**
 * 学习前缀树。
 */
public class TrieDemo {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord;
    }

    static class Trie {
        private final TrieNode root = new TrieNode();

        // 插入单词
        public void insert(String word) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
            }
            cur.isWord = true;
        }

        // 返回所有以 prefix 开头的单词
        public List<String> suggest(String prefix) {
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                cur = cur.children.get(c);
                if (cur == null) return Collections.emptyList();
            }

            List<String> result = new ArrayList<>();
            dfs(cur, new StringBuilder(prefix), result);
            return result;
        }

        private void dfs(TrieNode node, StringBuilder path, List<String> result) {
            if (node.isWord) {
                result.add(path.toString());
            }
            for (Map.Entry<Character, TrieNode> e : node.children.entrySet()) {
                path.append(e.getKey());
                dfs(e.getValue(), path, result);
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        String[] words = {"he", "hello", "help", "her", "hero", "heat", "apple"};
        for (String w : words) {
            trie.insert(w);
        }

        String input = "he";
        System.out.println("输入前缀: " + input);
        System.out.println("匹配结果: " + trie.suggest(input));
    }
}