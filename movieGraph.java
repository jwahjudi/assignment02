import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Queue;

/*
Made a graph that consist of a MovieNode and ActorNode. Each node consist of an ArrayList of
one another. This is to make connection between actors that may play in different movies a lot efficient.
 */
public class movieGraph {
    class MovieNode {
        ArrayList<ActorNode> actors;
        boolean visited;

        public MovieNode() {
            actors = new ArrayList<>();
            visited = false;
        }
    }

    class ActorNode {
        ArrayList<MovieNode> movies;
        boolean visited;
        String name;
        ActorNode prev;

        public ActorNode(String n) {
            movies = new ArrayList<>();
            visited = false;
            name = n;
            prev = null;
        }
    }

    Hashtable<String, ActorNode> actorHash;
    Queue<ActorNode> path;

    public movieGraph() {
        actorHash = new Hashtable<>();//For faster checking and getting
        path = new LinkedList<>(); //For BFS
    }

    /*
    Make a MovieNode. First check if the actor was already added into the Hashtable previously. If it is,
    update ActorNode's ArrayList by adding that MovieNode into the ArrayList. If not, make
    a new ActorNode, add it to the Hashtable, then add the MovieNode to the new ActorNode. Also
    for both, add the ActorNode into the MovieNode also.
     */
    public void makeMovieNode(String[] names) {
        MovieNode temp = new MovieNode();

        for (int i = 0; i < names.length; i++) {
            if (actorHash.containsKey(names[i])) {
                actorHash.get(names[i]).movies.add(temp);
                temp.actors.add(actorHash.get(names[i]));
            } else {
                ActorNode actor = new ActorNode(names[i]);
                actor.movies.add(temp);
                actorHash.put(names[i], actor);
            }
        }

    }

    /*
    Use BFS to search for shortest connection. Program would first iterate through ActorNode's movie or
    in other words, we go through each movie that the certain artist play in. When checking each MovieNode,
    iterate through each of its ActorNode too and see whether it is our target artist. While doing so, just
    like in Djikstra's algorithm, update that it is visited and the path that it came from(both initialized
    inside the ActorNode). At the end, add the ActorNode to the queue, so that we can dequeue and repeat the
    same step until we reach the target.
     */
    public boolean BFS(String src, String target)
    {
        ActorNode source = actorHash.get(src);
        source.visited = true;
        path.add(source);
        boolean found = false;
        while(!path.isEmpty() && found == false)
            found = BFS(path.remove(), actorHash.get(target));
        if(found == true)
            return true;
        else
            return false;

    }

    private boolean BFS(ActorNode curr, ActorNode target)
    {
        if(curr == target)
        {
            return true;
        }
        for(int i = 0; i < curr.movies.size(); i++)//iterate through each MovieNode
        {
            MovieNode movie = curr.movies.get(i);
            if (!movie.visited)
            {
                movie.visited = true;
                for (int j = 0; j < movie.actors.size(); j++)//iterate through each ActorNode in that MovieNode
                {
                    ActorNode actor = movie.actors.get(j);
                    if (!actor.visited)
                    {
                        actor.visited = true;
                        actor.prev = curr;
                        path.add(actor); //enqueue
                    }
                }
            }
        }
        return false;
    }

    public void printPath(String start, String src)
    {
        String print = src;
        ActorNode source = actorHash.get(src);
        while(source.prev != null)
        {
            source = source.prev;
            print = source.name + "->" + print;
        }
        System.out.println("Path between " + start + " and " + src + ": " +print);
    }

    public boolean containsKey(String arg)
    {
        return actorHash.containsKey(arg);
    }

    /*
    For input validation
     */
    public String capitalize(String arg)
    {
        String[] words = arg.split(" ");
        String str = "";
        for(int i = 0; i < words.length; i++)
        {
            str = str + words[i].substring(0,1).toUpperCase() + words[i].substring(1);
            if(i != words.length-1)
                str = str + " ";
        }
        return str;
    }
}
