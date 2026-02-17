package HTMLParser;

public class TextExtracter {

    public TextExtracter(String content) {
        boolean inTag = false;
        String tag = "";
        boolean isStyleTag = false;
        // boolean skipText = false;
        for (char c : content.toCharArray()) {
            if (c == '<') {
                inTag = true;
            } else if (c == '>') {
                inTag = false;
                if (tag.equals("style")) {
                    isStyleTag = true;
                }
                if(tag.equals("/style")){
                    isStyleTag = false;
                }
                tag = "";
            } else if (inTag) {
                tag = tag + c;
            } else if (!inTag) {
                if (isStyleTag) {
                    continue;
                } else {
                    System.out.print(c);
                }
            }
        }
    }
}
