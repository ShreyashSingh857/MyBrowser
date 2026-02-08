package HTMLParser;
public class TextExtracter{
    public TextExtracter(String content){
        boolean inTag = false;
        for(char c : content.toCharArray()){
            if(c == '<'){
               inTag = true; 
            }
            else if(c == '>'){
                inTag = false;
            }
            else if(!inTag){
                System.out.print(c);
            }
        }
    }
}