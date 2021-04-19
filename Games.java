package chatbot;
import java.util.*;


public class Games extends Domain
{

	private String [] gtopics = new String [] {"recommendation", "moba", "fps", "rpg", "league of legends", "valorant", "genshin", "description", "overview"};
	private Map <String, ArrayList <String>> sdkeywords = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> sdresponses = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> sdgenres = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> sdnames = new LinkedHashMap <String, ArrayList <String>> ();
	private String sdrequested;
	private String sdmaybe;
	private String sdresponse;
	
	// initializing all the maps of keywords, responses, and genres for games
	public Games()
	{
		sdrequested = "";
		sdmaybe = "";
		sdresponse = "";
		
	// add categories corresponding to each ArrayList of keywords
		sdkeywords.put("reckw", new ArrayList <String> (Arrays.asList("recommend", "should", "give", "name")));
		sdkeywords.put("about", new ArrayList <String> (Arrays.asList("about")));
		sdkeywords.put("play", new ArrayList <String> (Arrays.asList("do", "play", "how")));
		sdkeywords.put("g_genres", new ArrayList <String> (Arrays.asList("moba", "fps", "rpg", "any", "anything", "no"))); // 'no' preference
		sdkeywords.put("gkw_names", new ArrayList <String> (Arrays.asList("league", "lol", "valorant", "val", "genshin")));
	
	// add games for each genre; keys in order of 'all' listing, titles as listed in names
		sdgenres.put("all", new ArrayList <String> (Arrays.asList("moba", "fps", "rpg", "any", "anything", "no")));
		sdgenres.put("moba", new ArrayList <String> (Arrays.asList("League of Legends ")));
		sdgenres.put("fps", new ArrayList <String> (Arrays.asList("Valorant ")));
		sdgenres.put("rpg", new ArrayList <String> (Arrays.asList("Genshin Impact")));
		sdgenres.put("any", new ArrayList <String> (Arrays.asList("League of Legends ", "Valorant ", "Genshin Impact ")));
		sdgenres.put("anything", new ArrayList <String> (Arrays.asList("League of Legends ", "Valorant ", "Genshin Impact ")));
		sdgenres.put("no", new ArrayList <String> (Arrays.asList("League of Legends ", "Valorant ", "Genshin Impact ")));
		sdgenres.put("genrenotfound", new ArrayList <String> (Arrays.asList("Sorry, I don't know any game of that genre. ")));
		
	// add other names for each game under the game; keys in order of genre
		sdnames.put("all", new ArrayList <String> (Arrays.asList("League", "league", "LoL", "LOL", "lol", "Valorant", "valorant", "Val", "val", "Genshin", "genshin", "Impact", 
																"impact")));
		sdnames.put("lol", new ArrayList <String> (Arrays.asList("League", "league", "LoL", "LOL", "lol")));
		sdnames.put("val", new ArrayList <String> (Arrays.asList("Valorant", "valorant", "Val", "val")));
		sdnames.put("genshin", new ArrayList <String> (Arrays.asList("Genshin", "genshin", "Impact", "impact")));
		sdnames.put("genrenotfound", new ArrayList <String> (Arrays.asList("genrenotfound", "Sorry, I don't know any game of that genre.", "Which genre are you referring to?")));
		sdnames.put("titlenotfound", new ArrayList <String> (Arrays.asList("titlenotfound", "Sorry, but I'm not sure which game you're talking about.", 
															"Which game are you referring to? ")));
	
	// add bot responses for each keyword category; responses go in order of genre
		sdresponses.put("g_recrsp", new ArrayList <String> (Arrays.asList("What genre are you interested in? ")));
		sdresponses.put("genrenotfound", new ArrayList <String> (Arrays.asList("Which genre are you referring to? ", "Which genre are you referring to? ", 
																"Sorry, I don't know any game of that genre. ")));
		sdresponses.put("gamenotfound", new ArrayList <String> (Arrays.asList("Which game are you referring to? ", "Which game are you referring to? ", 
																"Sorry, but I'm not sure which game you're talking about. ")));
		sdresponses.put("lol", new ArrayList <String> (Arrays.asList("League of Legends ", "League of Legends is a 5v5 or 3v3 MOBA designed by Riot Games. ", 
																"You play as 'champions' to destroy the opponents 'nexus'. ")));
		sdresponses.put("val", new ArrayList <String> (Arrays.asList("Valorant ", "Valorant is a 5v5 FPS designed by Riot Games. ", 
																"You play as 'agents' to either plant or defuse a bomb, the 'spike' on specified sites. ")));
		sdresponses.put("genshin", new ArrayList <String> (Arrays.asList("Genshin Impact ", "Genshin Impact is an RPG designed by Mihoyo. ",
																"You explore the world of 'Teyvat' as a 'Traveler' who has been separated from their sibling by an Unknown God. ")));
	}
	
	public void findTopic(String query)
	{
		Scanner sc = new Scanner (System.in);
		
		// Recommendation -- genre in query
		if (isrecreq(query) && isgenre(query))
		{
			whatgenre(query);
			sdresponse = sdresponse +gamerec();
		}
		else if (isgenre(query))
		{
			whatgenre(query);
			sdresponse = sdresponse + gamerec();
		}
		
		// Recommendation -- genre NOT in query
		else if (isrecreq(query) && !isgenre(query))
			sdresponse = sdresponse + sdresponses.get("g_recrsp").get(0);
		
		// About -- title in query
		else if (isname(query) && isabout(query))
		{
			findgame(query);
			sdresponse = sdresponse + sdresponses.get(sdmaybe).get(1);
		}
		
		// About -- title NOT in query)
		else if (!isname(query) && isabout(query))
		{
			findgame(sdmaybe);
			sdresponse = sdresponse + sdresponses.get(sdmaybe).get(1);
		}
		
		// Play -- title in query
		else if (isname(query) && isplay(query))
		{
			findgame(query); 
			sdresponse = sdresponse + sdresponses.get(sdmaybe).get(2);
		}
		
		// Play -- title NOT in query
		else if (!isname(query) && isplay(query))
		{
			findgame(sdmaybe);
			sdresponse = sdresponse + sdresponses.get(sdmaybe).get(2);
		}
		else
			sdresponse = sdresponse + "Sorry, I'm not sure what you mean.";
	}
	
	public boolean isrecreq(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> reckw = sdkeywords.get("reckw");
		
		for(int i = 0; i < words.size(); i++)
		{	
			for (int j = 0; j < reckw.size(); j++)
			{
				if (words.get(i).equals(reckw.get(j)))
				{
					sdmaybe = "";
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isabout(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> about = sdkeywords.get("about");
		
		for(int i = 0; i < words.size(); i++)
		{	
			for (int j = 0; j < about.size(); j++)
			{
				if (words.get(i).equals(about.get(j)))
					return true;
			}
		}
		return false;
	}
	
	public boolean isplay(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> play = sdkeywords.get("play");
		
		for(int i = 0; i < words.size(); i++)
		{	
			for (int j = 0; j < play.size(); j++)
			{
				if (words.get(i).equals(play.get(j)))
					return true;
			}
		}
		return false;
	}
	
	public boolean isgenre(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> genre = sdkeywords.get("g_genres");
		
		for(int i = 0; i < words.size(); i++)
		{	
			for (int j = 0; j < genre.size(); j++)
			{
				if (words.get(i).equals(genre.get(j)))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isname(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> reckw = sdkeywords.get("gkw_names");
		
		for(int i = 0; i < words.size(); i++)
		{	
			for (int j = 0; j < reckw.size(); j++)
			{
				if (words.get(i).equals(reckw.get(j)))
				{
					sdmaybe = words.get(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public void whatgenre(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> genres = sdkeywords.get("g_genres");
		String genre = "genrenotfound";
		
		for(int i = 0; i < words.size(); i++)
		{	
			for (int j = 0; j < genres.size(); j++)
			{
				if (words.get(i).equals(genres.get(j)))
					genre = words.get(i);
			}
		}
		sdrequested = genre;
		if (sdrequested.equals("genrenotfound"))
			sdmaybe = "gamenotfound";
	}
	
	public String gamerec()
	{
		List <String> avail = sdgenres.get(sdrequested);
		int rand = (int) (Math.random()*avail.size());
		sdrequested = "";
		sdmaybe = avail.get(rand);
		return sdmaybe;
	}
	
	public String respond()
	{
		return sdresponse;
	}
	public void clearresponse()
	{
		sdresponse = "";
	}
	
	public String gettitle(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> names = sdnames.get("all");
		
		for(int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < names.size(); j++)
			{
				if (words.get(i).equals(names.get(j)))
					return words.get(i);
			}
		}
		return "titlenotfound";
	}
	
	public void findgame(String query)
	{
		String game = gettitle(query);
		
		List <String> leag = sdnames.get("lol");
		List <String> valo = sdnames.get("val");
		List <String> gens = sdnames.get("genshin");
		List <String> tnf = sdnames.get("titlenotfound");
		List <String> gnf = sdnames.get("genrenotfound");
		
		for (int i = 0; i < tnf.size(); i++)
		{
			if (game.equals(tnf.get(i)))
				sdmaybe = "titlenotfound";
		}
		for (int i = 0; i < gnf.size(); i++)
		{
			if (game.equals(gnf.get(i)))
				sdmaybe = "genrenotfound";
		}
		for (int i = 0; i < leag.size(); i++)
		{
			if (game.equals(leag.get(i)))
				sdmaybe = "lol";
		}
		for (int i = 0; i < valo.size(); i++)
		{
			if (game.equals(valo.get(i)))
				sdmaybe = "val";
		}
		for (int i = 0; i < gens.size(); i++)
		{
			if (game.equals(gens.get(i)))
				sdmaybe = "genshin";
		}
	}
	
}