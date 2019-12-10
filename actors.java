
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class actors
{
    public static void main(String[] args) throws Exception
    {
        /*
        Parsing the file to JSON and then getting all the names of the artist playing in that movie
         */
        movieGraph graph = new movieGraph();
        JSONParser jsonParser = new JSONParser();
        Scanner file = new Scanner(new File(args[0]));
        String line = file.nextLine();
        JSONArray movieInfo = null;
        while(file.hasNextLine())
        {
            line = file.nextLine();
            int start = line.indexOf("[{");
            if(start != -1) //some line does not include "[{" and they empty so check for that and skip it
            {
                String cred = line.substring(line.indexOf("[{"), line.indexOf("}]")+2);//getting only the actor/actress information
                cred = cred.replace("\"\"", "\"");
                cred = cred.replace("]\",\"[", "");
                movieInfo = (JSONArray) jsonParser.parse(cred);//parse into JSON
                JSONObject movie = null;
                String name;
                String[] temp = new String[movieInfo.size()];
                for(int i = 0; i < movieInfo.size(); i++)
                {
                    movie = (JSONObject) movieInfo.get(i);
                    name = (String) movie.get("name");
                    temp[i] = name;
                }
                graph.makeMovieNode(temp);
            }
        }

        /*
        Getting user input and validating their input
         */
        Scanner input = new Scanner(System.in);
        System.out.print("Actor 1 name: ");
        String source = graph.capitalize(input.nextLine());
        if(!graph.containsKey(source))
        {
            System.out.println("No such actor.");
            return;
        }
        System.out.print("Actor 2 name: ");
        String target = graph.capitalize(input.nextLine());
        if(!graph.containsKey(target))
        {
            System.out.println("No such actor.");
            return;
        }

        /*
        Searching for shortest connection
         */
        if(graph.BFS(source , target))
            graph.printPath(source, target);
        else
            System.out.println("No path between these two");
    }

}
