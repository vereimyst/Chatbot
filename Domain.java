package chatbot;
import java.util.*;

/* Notes:
		someone said to implement weight of keywords? greetings = 0, other categories = 1, ... , goodbye = max
		for (Map.Entry<String, ArrayList <String>> kws : keywords.entrySet()) 
		{
	        System.out.print(kws.getKey() + ":");
	        System.out.println(kws.getValue());
	    }
*/

public class Domain
{	
	// hard coding all maps & arraylists to start, change to iter/easier add/del if time
	private Map <String, ArrayList <String>> keywords = new LinkedHashMap <String, ArrayList <String>> (); // example, {greeting: [hi,hello], goodbye: [bye]}
	private Map <String, ArrayList <String>> responses = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> genres = new LinkedHashMap <String, ArrayList <String>> ();
	private ArrayList <String> kws = new ArrayList <String> ();
	private Map <String, Domain> domain = new LinkedHashMap <String, Domain> ();
	private String override;
	//private String original; store the original input string?
	private String last;
	private boolean leave;
	private String response;
	private boolean error;
	
	public Domain() {}
	public Domain(Games g, Manga m, Anime a)
	{	
		domain.put("games", g);
		domain.put("manga", m);
		domain.put("anime", a);
		override = "";
		last = "";
		response = "";
		leave = false;
		error = false;
		
	// adding categories corresponding to each ArrayList of keywords
		keywords.put("all", new ArrayList <String> (Arrays.asList("hi", "hello", "hey", "bye", "cya", "goodbye", "byebye", "ty", "thank", "thanks", "appreciate", "game", 
														"games", "moba", "rpg", "fps", "league", "lol", "val", "valorant", "genshin", "manga", "given", "yona", "law", 
														"trash", "tcf", "anime", "given", "tokyo", "violet", "about", "summary", "eps", "episode", "ep", "episodes", 
														"season", "seasons", "read", "complete", "completed", "translated", "long", "status", "length", "watch", "play")));
		// keywords.put("rec", new ArrayList <String> (Arrays.asList("recommend", "should", "name", "give")));
		keywords.put("greeting", new ArrayList <String> (Arrays.asList("hi", "hello", "hey")));
		keywords.put("goodbye", new ArrayList <String> (Arrays.asList("bye", "cya", "goodbye", "byebye")));
		keywords.put("thank", new ArrayList <String> (Arrays.asList("ty", "thank", "thanks", "appreciate")));
		keywords.put("ques", new ArrayList <String> (Arrays.asList("about", "summary", "long", "status", "length", "read", "watch")));
		keywords.put("games", new ArrayList <String> (Arrays.asList("game", "games", "moba", "rpg", "fps", "league", "lol", "val", "valorant", "genshin", "play")));
		keywords.put("manga", new ArrayList <String> (Arrays.asList("manga", "law", "trash", "tcf", "read", "complete", "completed", "translated", "read")));
		keywords.put("anime", new ArrayList <String> (Arrays.asList("anime", "tokyo", "violet", "eps", "episode", "ep", "episodes", "season", "seasons", "watch")));
		keywords.put("amtitles", new ArrayList <String> (Arrays.asList("given", "yona")));
		keywords.put("error", new ArrayList <String> (Arrays.asList()));
		
	// adding genres to corresponding
		genres.put("rec", new ArrayList <String> (Arrays.asList("manga", "anime", "games", "any")));
		genres.put("all", new ArrayList <String> (Arrays.asList("school", "harem", "historical", "horror", "music", "psychological", "any", "anything", "no", "comedy", 
														"drama", "romance", "mystery", "supernatural", "seinen", "shoujo", "shounen", "bl", "yaoi", "action", "adventure", 
														"fantasy", "slice")));
		genres.put("manga", new ArrayList <String> (Arrays.asList("school", "harem", "historical")));
		genres.put("anime", new ArrayList <String> (Arrays.asList("horror", "music", "psychological")));
		genres.put("games", new ArrayList <String> (Arrays.asList("moba", "fps", "rpg")));
		genres.put("both", new ArrayList <String> (Arrays.asList("any", "anything", "no", "comedy", "drama", "romance", "mystery", "supernatural", "seinen", "shoujo", 
														"shounen", "bl", "yaoi", "action", "adventure", "fantasy", "slice")));
	
	// add bot responses for each keyword category, general responses only
		responses.put("greeting", new ArrayList <String> (Arrays.asList("Hey there! ", "Hello! ", "Hi! ")));
		responses.put("goodbye", new ArrayList <String> (Arrays.asList("See you next time!", "Good talking with you!", "Goodbye!")));
		responses.put("thank", new ArrayList <String> (Arrays.asList("No problem! ", "You're welcome! ", "Anytime! ")));
		responses.put("notfound", new ArrayList <String> (Arrays.asList("Sorry, I don't understand.")));
	}
	
	// determine which class to go into
	public Domain determineDomain(Map <String, Domain> domains)
	{
		// adding my subclasses to a map of a string to subdomain (Domain will refer to general responses like "greeting" and "goodbye")
		if (isgreeting() || isgoodbye() || isthank()) 	// DO THIS LASTTTTTTTTTT
		{
			last = "general";
			return domains.get("general");
		}
		else if (!override.equals(""))					// if an override kw is in the query
		{
			kws.clear();
			last = override;
			override = "";
			return domains.get(last);
		}
		
		// for genre specific cases
		else if (isgames() || ismanga() || isanime())	// if game, manga, or anime kw is in the query
		{
			if (isgames())
			{
				kws.clear();
				last = "games";
				return domains.get("games");
			}
			else if (ismanga())
			{
				kws.clear();
				last = "manga";
				return domains.get("manga");
			}
			else if (isanime())
			{
				kws.clear();
				last = "anime";
				return domains.get("anime");
			}
		}
		
		// for general question cases (e.g. "what is it about", "how long is it")
		else if (!last.equals(""))			// if no override or gma kw AND there is a last kw and ques word
		{
			kws.clear();
			return domains.get(last);
		}
		else
		{
			error = true;
			return domains.get("general");
		}
		
		kws.clear();
		return null;
	}
	
	// will only be accessed if isgreeting(), isthank(), or isgoodbye()
	public void findTopic(String query)
	{	
		if (isgreeting())
		{
			response = greet() + response;
			removekws("greeting");
		}
		if (isthank())
		{
			response = thank() + response;
			removekws("thank");
		}
		if (isgoodbye())
		{
			response = response + bye();
			removekws("goodbye");
			leave = true;
		}
		if (error)
			response = response + responses.get("notfound").get(0);
		error = false;
	}
	
	// get response
	public String respond()
	{
		response = response + domain.get("games").respond() + domain.get("manga").respond() + domain.get("anime").respond();
		return response;
	}
	// add to response
	public void addresponse(String part)
	{
		response = response + part;
	}
	// clears response
	public void clearresponse()
	{
		response = "";
		domain.get("games").clearresponse();
		domain.get("manga").clearresponse();
		domain.get("anime").clearresponse();
	}
	public boolean getleave()
	{
		return leave;
	}
	
	// finds all keywords and genres in a query and puts them in kws array
	public void findkws(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList<String>();
		words = Arrays.asList(str);
		
		List <String> allkeywords = keywords.get("all");
		List <String> allgenres = genres.get("all");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < allkeywords.size(); j++)
			{
				if (words.get(i).equals(allkeywords.get(j)))
					kws.add(words.get(i));
			}
			for (int j = 0; j < allgenres.size(); j++)
			{
				if (words.get(i).equals(allgenres.get(j)))
					kws.add(words.get(i));
			}
		}
		
		if (isQueryAmbiguous())
			clarification();
	}
	
	// check if any keywords remain in kws
	public boolean iskey()
	{
		if (kws.size() == 0)
			return false;
		else
			return true;
	}
	
	// remove a keyword from kws
	public void removekws(String kw)
	{
		List <String> hold = keywords.get(kw);
		String removed = "";
		boolean found = false;
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < hold.size(); j++)
			{
				if (kws.get(i).equals(hold.get(j)))
				{
					found = true;
					break;
				}
			}
			if (found) 
			{
			    kws.remove(i);
			    break;
			}
		}
	}
	
	// checks if any genres is in kws
	public boolean isgenre()
	{
		List <String> genre = genres.get("all");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < genre.size(); j++)
			{
				if (kws.get(i).equals(genre.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if manga genres is in kws
	public boolean is_mgenre()
	{
		List <String> mgenre = genres.get("manga");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < mgenre.size(); j++)
			{
				if (kws.get(i).equals(mgenre.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if anime genres is in kws
	public boolean is_agenre()
	{
		List <String> agenre = genres.get("anime");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < agenre.size(); j++)
			{
				if (kws.get(i).equals(agenre.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if amtitles is in kws
	public boolean is_amtitles()
	{
		List <String> amtitle = keywords.get("amtitles");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < amtitle.size(); j++)
			{
				if (kws.get(i).equals(amtitle.get(j)))
					return true;
			}
		}
		return false;
	}
	// checks if both genres is in kws
	public boolean is_bothgenre()
	{
		List <String> bothgenre = genres.get("both");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < bothgenre.size(); j++)
			{
				if (kws.get(i).equals(bothgenre.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if games is in kws
	public boolean isgames()
	{
		List <String> games = keywords.get("games");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < games.size(); j++)
			{
				if (kws.get(i).equals(games.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if manga is in kws
	public boolean ismanga()
	{
		List <String> manga = keywords.get("manga");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < manga.size(); j++)
			{
				if (kws.get(i).equals(manga.get(j)) && override.equals(""))
					return true;
				else if (override.equals("manga"))
					return true;
			}
		}
		return false;
	}
	
	// checks if anime is in kws
	public boolean isanime()
	{
		List <String> anime = keywords.get("anime");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < anime.size(); j++)
			{
				if (kws.get(i).equals(anime.get(j)) && override.equals(""))
					return true;
				else if (override.equals("anime"))
					return true;
			}
		}
		return false;
	}
	
	// checks if ques is in kws
	public boolean isques()
	{
		List <String> ques = keywords.get("ques");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < ques.size(); j++)
			{
				if (kws.get(i).equals(ques.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if greeting is in kws
	public boolean isgreeting()
	{
		List <String> greet = keywords.get("greeting");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < greet.size(); j++)
			{
				if (kws.get(i).equals(greet.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if goodbye is in kws
	public boolean isgoodbye()
	{
		List <String> bye = keywords.get("goodbye");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < bye.size(); j++)
			{
				if (kws.get(i).equals(bye.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// checks if thank is in kws
	public boolean isthank()
	{
		List <String> thank = keywords.get("thank");
		
		for (int i = 0; i < kws.size(); i++)
		{
			for (int j = 0; j < thank.size(); j++)
			{
				if (kws.get(i).equals(thank.get(j)))
					return true;
			}
		}
		return false;
	}
	
	public String greet()
	{
		List <String> output = responses.get("greeting");
		int x = (int)(Math.random()*3);
		return output.get(x);
	}
	
	public String bye()
	{
		List <String> output = responses.get("goodbye");
		int x = (int)(Math.random()*3);
		
		return output.get(x);
	}
	
	public String thank()
	{
		List <String> output = responses.get("thank");
		int x = (int)(Math.random()*3);
		return output.get(x);
	}
	
	public boolean isQueryAmbiguous()
	{
		if (last.equals("anime") || last.equals("manga") || ismanga() || isanime())
			return false;
		else if (is_amtitles() && is_bothgenre() && (!ismanga() || !isanime()))
			return true;
		else if ((is_bothgenre() || is_amtitles()) && !ismanga() && !isanime())
			return true;
		else
			return false;
	}
	
	public void clarification()
	{
		Scanner sc = new Scanner (System.in);
		if (ismanga())
		{
			System.out.println("Are you referring to the manga or the anime?");
			String temp = sc.nextLine();
			findkws(temp);
			if (ismanga())
				override = "manga";
			if (isanime())
				override = "anime";
		}
		else				// the only other case I need to consider is genre since this will only be implemented if isQueryAmbiguous returns true, thus else is sufficient
		{
			System.out.println("Are you interested in a manga or an anime?");
			String temp = sc.nextLine();
			findkws(temp);
			if (ismanga())
				override = "manga";
			
			else if (isanime())
				override = "anime";
			else
			{
				int rand = (int) (Math.random()*2);
				override = genres.get("rec").get(rand);
			}
		}
	}

}