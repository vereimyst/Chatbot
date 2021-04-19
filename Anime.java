package chatbot;
import java.util.*;


public class Anime extends Domain
{
	private String [] atopics = new String [] {"anime", "given", "akatsuki no yona", "yona of the dawn", "tokyo ghoul", "violet evergarden", "recommend"};
	private Map <String, ArrayList <String>> akeywords = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> sdresponses = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> agenres = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> anames = new LinkedHashMap <String, ArrayList <String>> ();
	private String arequested;
	private String amaybe;
	private String sdresponse;
	
	// initializing all the maps of keywords, responses, and genres for anime
	public Anime ()
	{
		sdresponse = "";
		arequested = "";
		amaybe = "titlenotfound";
		
	// add categories corresponding to each ArrayList of keywords
		akeywords.put("a_reckw", new ArrayList <String> (Arrays.asList("recommend", "should", "give", "name")));
		akeywords.put("a_sumkw", new ArrayList <String> (Arrays.asList("about", "summary")));
		akeywords.put("a_statkw", new ArrayList <String> (Arrays.asList("how", "much", "long", "eps", "episode", "ep", "episodes", "season", "seasons", "length", "status")));
		akeywords.put("akw_names", new ArrayList <String> (Arrays.asList("given", "yona", "tokyo", "violet")));
		akeywords.put("alla_genres", new ArrayList <String> (Arrays.asList("action", "drama", "romance", "mystery", "slice", "fantasy", "shounen", "bl", "yaoi", "adventure", 
															"horror", "music", "comedy", "psychological", "shoujo", "supernatural", "seinen", "any", "anything", "no"))); //'no' preference
		
		/*	  Given: 		 drama, romance, 'slice' of life, 		   'shounen' ai, music
	 	   Akatsuki: action, 		romance, 				  fantasy, adventure, comedy, shoujo
 		Tokyo Ghoul: action, drama, mystery, 						   horror, psychological, supernatural, seinen
			 Violet: 		 drama, 		 'slice' of life, fantasy						*/
	
	// add anime for each genre; keys in order of 'all' listing, titles as listed in names
		agenres.put("any", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", "Akatsuki no Yona (Yona of the Dawn), animated by Pierrot Co. (PG-13))", 
															"Tokyo Ghoul (animated by Pierrot Co. (R))", "Violet Evergarden (animated by Kyoto Animation (PG-13))")));
		agenres.put("anything", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", "Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))", 
															"Tokyo Ghoul (animated by Pierrot Co. (R))", "Violet Evergarden (animated by Kyoto Animation (PG-13))")));
		agenres.put("no", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", "Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))", 
															"Tokyo Ghoul (animated by Pierrot Co. (R))", "Violet Evergarden (animated by Kyoto Animation (PG-13))")));
		agenres.put("action", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))", "Tokyo Ghoul (animated by Pierrot Co. (R))")));
		agenres.put("drama", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", "Tokyo Ghoul (animated by Pierrot Co. (R))", "Violet Evergarden (animated by Kyoto Animation (PG-13))")));
		agenres.put("romance", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", "Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))")));
		agenres.put("mystery", new ArrayList <String> (Arrays.asList("Tokyo Ghoul (animated by Pierrot Co. (R))")));
		agenres.put("slice", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", "Violet Evergarden (animated by Kyoto Animation (PG-13))")));
		agenres.put("fantasy", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))", "Violet Evergarden (animated by Kyoto Animation (PG-13))")));
		agenres.put("shounen", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))")));
		agenres.put("bl", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))")));
		agenres.put("yaoi", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))")));
		agenres.put("adventure", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))")));
		agenres.put("horror", new ArrayList <String> (Arrays.asList("Tokyo Ghoul (animated by Pierrot Co. (R))")));
		agenres.put("music", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))")));
		agenres.put("comedy", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))")));
		agenres.put("shoujo", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))")));
		agenres.put("psychological", new ArrayList <String> (Arrays.asList("Tokyo Ghoul (animated by Pierrot Co. (R))")));
		agenres.put("supernatural", new ArrayList <String> (Arrays.asList("Tokyo Ghoul (animated by Pierrot Co. (R))")));
		agenres.put("seinen", new ArrayList <String> (Arrays.asList("Tokyo Ghoul (animated by Pierrot Co. (R))")));
		agenres.put("genrenotfound", new ArrayList <String> (Arrays.asList("Sorry, I don't know any anime of that genre.")));

	// add other names for each game under the game; keys in order of genre
		anames.put("all", new ArrayList <String> (Arrays.asList("Given", "given", "Yona", "yona", "Akatsuki", "akatsuki", "Dawn", "dawn", "Tokyo", "tokyo", "Ghoul", "ghoul", 
															"Violet", "violet", "Evergarden", "evergarden")));
		anames.put("given", new ArrayList <String> (Arrays.asList("Given", "given")));
		anames.put("yona", new ArrayList <String> (Arrays.asList("Yona", "yona", "Akatsuki", "akatsuki", "Dawn", "dawn")));
		anames.put("tokyo", new ArrayList <String> (Arrays.asList("Tokyo", "tokyo", "Ghoul", "ghoul")));
		anames.put("violet", new ArrayList <String> (Arrays.asList("Violet", "violet", "Evergarden", "evergarden")));
		anames.put("genrenotfound", new ArrayList <String> (Arrays.asList("genrenotfound", "Sorry, I don't know any anime of that genre.", "Which genre are you referring to?")));
		anames.put("titlenotfound", new ArrayList <String> (Arrays.asList("titlenotfound", "Sorry, but I'm not sure which anime you're talking about.", 
															"Which anime are you referring to?")));
	
	// add bot responses for each keyword category; responses go in order of anime as listed in names ^
		sdresponses.put("a_recresp", new ArrayList <String> (Arrays.asList("What genre are you interested in?")));
		sdresponses.put("genrenotfound", new ArrayList <String> (Arrays.asList("Which genre are you referring to?", 
															"Which genre are you referring to?", 
															"Sorry, I don't know any anime of that genre.")));
		sdresponses.put("titlenotfound", new ArrayList <String> (Arrays.asList("Which anime are you referring to?", 
															"Which anime are you referring to?", 
															"Sorry, but I'm not sure which anime you're talking about.")));
		sdresponses.put("given", new ArrayList <String> (Arrays.asList("Given (animated by Lerche (PG-13))", 
															"Given is about highschooler Mafuyu Satou learning to play the guitar while coming to terms with the mysterious circumstances that led him to be its owner.", 
															"Given finished airing Aug. 22, 2020. It has one season (11 episodes, 22 min. per ep.) and a movie sequel (59 min.); a second season is expected to be released in 2022 or 2023.")));
		sdresponses.put("yona", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (animated by Pierrot Co. (PG-13))", 
															"Akatsuki no Yona (Yona of the Dawn) is about Princess Yona, left with only the words of a prophecy, striving to restore Kouka to its former glory while being pursued relentlessly by the forces of the usurper King.", 
															"Akatsuki no Yona (Yona of the Dawn) finished airing Dec. 20, 2016. It has one season (24 episodes, 24 min. per ep.) and an OVA (3 episodes, 23 min. per ep.); it is unknown whether Pierrot Co. will release a second season.")));
		sdresponses.put("tokyo", new ArrayList <String> (Arrays.asList("Tokyo Ghoul (animated by Pierrot Co. (R))", 
															"Tokyo Ghoul is about college student Kaneki Ken learning to live as a half human, half ghoul, shunned by both sides, in a time of tension.", 
															"Tokyo Ghoul finished airing Dec. 25, 2018. It has four seasons (12 episodes per season, 24 min. per ep.) and two OVAs (1 episode per OVA, one is 30 min., the other is 24 min); hopes for fifth season have already been officially dismissed.")));
		sdresponses.put("violet", new ArrayList <String> (Arrays.asList("Violet Evergarden (animated by Kyoto Animation (PG-13))", 
															"Violet Evergarden is about a former child soldier Violet Evergarden becoming an Auto Memory Doll to learn how to live without orders and the meaning of 'aishiteru'.", 
															"Violet Evergarden finished airing Sep. 18, 2020. It has one season (13 episodes, 24 min. per ep.), a special episode (34 min.), and 2 movies (one is 1 hr. 31 min., the other is 2 hr. 20 min.); KyoAni is reported to have have begun preproduction and the second season is expected to drop at the end of 2021 or the beginning of 2022.")));
	}

	// check each question case and provide a response
	public void findTopic(String query)
	{
		Scanner sc = new Scanner (System.in);
		
		// Recommendation -- genre in query
		if (isrecreq(query) && isgenre(query))
		{
			whatgenre(query);
			sdresponse = sdresponse + animerec();
		}
		else if (isgenre(query))
		{
			whatgenre(query);
			sdresponse = sdresponse + animerec();
		}
		
		// Recommendation -- genre NOT in query
		else if (isrecreq(query) && !isgenre(query))
			sdresponse = sdresponse + sdresponses.get("a_recresp").get(0);
		
		// Summary -- title in query
		else if (isname(query) && issumreq(query))
		{
			findanime(query);
			sdresponse = sdresponse + sdresponses.get(amaybe).get(1);
		}
		
		// Summary -- title NOT in query)
		else if (!isname(query) && issumreq(query))
		{
			findanime(amaybe);
			sdresponse = sdresponse + sdresponses.get(amaybe).get(1);
		}
		
		// Status -- title in query
		else if (isname(query) && isstatreq(query))
		{
			findanime(query);
			sdresponse = sdresponse + sdresponses.get(amaybe).get(2);
		}
			
		// Status -- title NOT in query
		else if (!isname(query) && isstatreq(query))
		{
			findanime(amaybe);
			sdresponse = sdresponse + sdresponses.get(amaybe).get(2);
		}
		else
			sdresponse = sdresponse + "Sorry, I'm not sure what you mean.";
		
	}
	
	//checks if there is a status key word in the query
	public boolean isstatreq(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
		
		List <String> statkw = akeywords.get("a_statkw");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < statkw.size(); j++)
			{
				if (words.get(i).equals(statkw.get(j)))
				{
					return true;
				}
			}
		}
		return false;
	}
		
	//checks if there is a summary key word in the query
	public boolean issumreq(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
		
		List <String> sumkw = akeywords.get("a_sumkw");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < sumkw.size(); j++)
			{
				if (words.get(i).equals(sumkw.get(j)))
				{
					return true;
				}
			}
		}
		return false;
	}
		
	// checks if there is a recommendation key word in the query
	public boolean isrecreq(String query)
	{
		String str[] = query.split(" ");
			List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
			
		List <String> reckw = akeywords.get("a_reckw");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < reckw.size(); j++)
				{
				if (words.get(i).equals(reckw.get(j)))
				{
					amaybe = "";
					return true;
				}
			}
		}
			return false;
	}
	
	// checks if there is a name in the current query
	public boolean isname(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
		
		List <String> names = akeywords.get("akw_names");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < names.size(); j++)
			{
				if (words.get(i).equals(names.get(j)))
				{
					amaybe = words.get(i);
					return true;
				}
			}
		}
		return false;
	}
	
	// checks if there is a genre in the current query
	public boolean isgenre(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
		
		List <String> genres = akeywords.get("alla_genres");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < genres.size(); j++)
			{
				if (words.get(i).equals(genres.get(j)))
				{
					amaybe = "titlenotfound";
					return true;
				}
			}
		}
		return false;
	}
	
	// finds a genre keyword in the query and sets arequested to that genre (or notfound)
	public void whatgenre(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
		
		List <String> genres = akeywords.get("alla_genres");
		String genre = "genrenotfound";
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < genres.size(); j++)
			{
				if (words.get(i).equals(genres.get(j)))
				{
					genre = words.get(i);
				}
			}
		}
		arequested = genre;
		if (arequested.equals("genrenotfound"))
			amaybe = "titlenotfound";
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
		
		List <String> names = anames.get("all");
		
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
	
	public void findanime(String query)
	{
		String name = gettitle(query);
		
		List <String> given = anames.get("given");
		List <String> yona = anames.get("yona");
		List <String> tokyo = anames.get("tokyo");
		List <String> violet = anames.get("violet");
		List <String> tnf = anames.get("titlenotfound");
		List <String> gnf = anames.get("genrenotfound");
		
		for (int i = 0; i < tnf.size(); i++)
		{
			if (name.equals(tnf.get(i)))
				amaybe = "titlenotfound";
		}
		for (int i = 0; i < gnf.size(); i++)
		{
			if (name.equals(gnf.get(i)))
				amaybe = "genrenotfound";
		}
		
		for (int i = 0; i < given.size(); i++)
		{
			if (name.equals(given.get(i)))
				amaybe = "given";
		}
		for (int i = 0; i < yona.size(); i++)
		{
			if (name.equals(yona.get(i)))
				amaybe = "yona";
		}
		for (int i = 0; i < tokyo.size(); i++)
		{
			if (name.equals(tokyo.get(i)))
				amaybe = "tokyo";
		}
		for (int i = 0; i < violet.size(); i++)
		{
			if (name.equals(violet.get(i)))
				amaybe = "violet";
		}
	}
	
	// randomly finds an anime of the requested genre and resets the value of arequested
	public String animerec()
	{
		List <String> avail = agenres.get(arequested);
		int rand = (int) (Math.random()*avail.size());
		arequested = "";
		amaybe = avail.get(rand);
		return amaybe;
	}
	
}