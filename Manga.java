package chatbot;
import java.util.*;


public class Manga extends Domain
{
	private Map <String, ArrayList <String>> mkeywords = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> mresponses = new LinkedHashMap <String, ArrayList <String>> ();
	private Map <String, ArrayList <String>> mgenres = new LinkedHashMap <String, ArrayList<String>> ();
	private Map <String, ArrayList <String>> mnames = new LinkedHashMap <String, ArrayList <String>> ();
	private String mrequested;
	private String mmaybe;	// temporarily save the title of a manga
	private String sdresponse;
	
	// initializing all the maps of keywords, responses, and genres for manga
	public Manga()
	{
		mrequested = "";
		mmaybe = "titlenotfound";
		sdresponse = "";
		
	// add categories corresponding to each ArrayList of keywords
		mkeywords.put("m_reckw", new ArrayList <String> (Arrays.asList("recommend", "should", "give", "name")));
		mkeywords.put("m_sumkw", new ArrayList <String> (Arrays.asList("about", "summary")));
		mkeywords.put("m_statkw", new ArrayList <String> (Arrays.asList("how", "much", "read", "length", "complete", "completed", "translated", "long", "status")));
		mkeywords.put("mkw_names", new ArrayList <String> (Arrays.asList("given", "yona", "law", "trash", "tcf")));
		mkeywords.put("allm_genres", new ArrayList <String> (Arrays.asList("comedy", "drama", "romance", "school", "action", "adventure", "fantasy", "slice", "shoujo", "shounen", "bl", 
															"yaoi", "harem", "historical", "seinen", "mystery", "supernatural", "any", "anything", "no"))); //'no' preference
		
		/*	 Given: comedy, drama, 		romance, 'school' life,				 'slice' of life, 		  shounen-ai,
		Inso's Law: comedy, drama, 		romance, 'school' life,     fantasy, 'slice' of life, shoujo, harem
		  Akatsuki: comedy, drama, 		romance, action, adventure, fantasy, 				  shoujo, historical, mystery, supernatural
		  	   TCF: comedy, drama, 				 action, adventure, fantasy, 						  seinen		*/
	
	// add manga for each genre; keys in order of 'all' listing, titles as listed in names
		mgenres.put("any", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", "Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("anything", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", "Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("no", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", "Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("comedy", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", "Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("drama", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", "Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("romance", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", "Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ")));
		mgenres.put("school", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ")));
		mgenres.put("action", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("fantasy", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("slice", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ")));
		mgenres.put("shoujo", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ")));
		mgenres.put("shounen", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ")));
		mgenres.put("bl", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ")));
		mgenres.put("yaoi", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu. ")));
		mgenres.put("harem", new ArrayList <String> (Arrays.asList("Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ")));
		mgenres.put("historical", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ")));
		mgenres.put("mystery", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ")));
		mgenres.put("supernatural", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ")));
		mgenres.put("seinen", new ArrayList <String> (Arrays.asList("The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ")));
		mgenres.put("genrenotfound", new ArrayList <String> (Arrays.asList("Sorry, I don't know any manga of that genre.")));
		
	// add other names for each game under the game; keys in order of genre
		mnames.put("all", new ArrayList <String> (Arrays.asList("Given", "given", "Yona", "yona", "Akatsuki", "akatsuki", "Dawn", "dawn", "Law", "law", "Inso's", "inso's", 
															"Webnovels", "webnovels", "tcf", "trash", "TCF", "Trash")));
		mnames.put("given", new ArrayList <String> (Arrays.asList("Given", "given")));
		mnames.put("yona", new ArrayList <String> (Arrays.asList("Yona", "yona", "Akatsuki", "akatsuki", "Dawn", "dawn")));
		mnames.put("law", new ArrayList <String> (Arrays.asList("Law", "law", "Inso's", "inso's", "Webnovels", "webnovels")));
		mnames.put("tcf", new ArrayList <String> (Arrays.asList("tcf", "trash", "TCF", "Trash")));
		mnames.put("genrenotfound", new ArrayList <String> (Arrays.asList("genrenotfound", "Sorry, I don't know any manga of that genre.", "Which genre are you referring to?")));
		mnames.put("titlenotfound", new ArrayList <String> (Arrays.asList("titlenotfound", "Sorry, but I'm not sure which manga you're talking about.", 
															"Which manga are you referring to?")));
		
	// add bot responses for each manga (in order of inname); responses in order of outname, summary, status
		mresponses.put("m_recrsp", new ArrayList <String> (Arrays.asList("What genre are you interested in?")));
		mresponses.put("genrenotfound", new ArrayList <String> (Arrays.asList("Which genre are you referring to?", 
															"Which genre are you referring to?", 
															"Sorry, I don't know any manga of that genre.")));
		mresponses.put("titlenotfound", new ArrayList <String> (Arrays.asList("Which manga are you referring to?", 
															"Which manga are you referring to?", 
															"Sorry, but I'm not sure which manga you're talking about.")));
		mresponses.put("given", new ArrayList <String> (Arrays.asList("Given (JP), Creator: Natsuki Kizu", 
															"Given is about a high school boy learning to cope with the loss of his beloved through playing music.", 
															"Both the manga and the scanlation are in progress. There are 6 complete volumes in the language of origin, and the English scanlation is at volume 4.")));
		mresponses.put("yona", new ArrayList <String> (Arrays.asList("Akatsuki no Yona (Yona of the Dawn) (JP), Creator: Mizuho Kusanagi ", 
															"Akatsuki no Yona is about a young girl, the former princess of the kingdom of Kouka, finding her own strength after losing everything.", 
															"Both the manga and the scanlation are in progress. There are 35 complete volumes in the language of origin, and the English scanlation is at volume 28.")));
		mresponses.put("law", new ArrayList <String> (Arrays.asList("Inso's Law (The Law of Webnovels) (KR), Author: Han-ryeo Yu, Artist: Hyeon A ", 
															"Inso's Law is about a high school girl who suddenly transmigrates into the world of a webnovel as the main character's unassuming best friend.", 
															"Both the manga and the scanlation are in progress. There are 100 chapters in the language of origin, and the English scanlation is at chapter 85.")));
		mresponses.put("trash", new ArrayList <String> (Arrays.asList("The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ", 
															"Trash of the Count's Family is about the journey of a young man who dreams of becoming the ultimate slacker.", 
															"Both the manga and the scanlation are in progress. There are 47 chapters in the language of origin, and the English scanlation is at chapter 31.")));
		mresponses.put("tcf", new ArrayList <String> (Arrays.asList("The Trash of the Count's Family (KR), Author: Ryeo Han Yu and Narae Byeol, Artist: PAN4 ", 
															"Trash of the Count's Family is about the journey of a young man who dreams of becoming the ultimate slacker.", 
															"Both the manga and the scanlation are in progress. There are 47 chapters in the language of origin, and the English scanlation is at chapter 31.")));
	}
	
	public void findTopic(String query)
	{
		Scanner sc = new Scanner (System.in);
		
		// Recommendation -- genre in query
		if (isrecreq(query) && isgenre(query))
		{
			whatgenre(query);
			sdresponse = sdresponse + mangarec();
		}
		else if (isgenre(query))
		{
			whatgenre(query);
			sdresponse = sdresponse + mangarec();
		}
		
		// Recommendation -- genre NOT in query
		else if (isrecreq(query) && !isgenre(query))
			sdresponse = sdresponse + mresponses.get("m_recrsp").get(0);
		
		// Summary -- title in query
		else if (isname(query) && issumreq(query))
		{
			findmanga(query);
			sdresponse = sdresponse + mresponses.get(mmaybe).get(1);
		}
		
		// Summary -- title NOT in query)
		else if (!isname(query) && issumreq(query))
		{
			findmanga(mmaybe);
			sdresponse = sdresponse + mresponses.get(mmaybe).get(1);
		}
		
		// Status -- title in query
		else if (isname(query) && isstatreq(query))
		{
			findmanga(query);
			sdresponse = sdresponse + mresponses.get(mmaybe).get(2);
		}
		
		// Status -- title NOT in query
		else if (!isname(query) && isstatreq(query))
		{
			findmanga(mmaybe);
			sdresponse = sdresponse + mresponses.get(mmaybe).get(2);
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
		
		List <String> statkw = mkeywords.get("m_statkw");
		
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
		
		List <String> sumkw = mkeywords.get("m_sumkw");
		
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
		
		List <String> reckw = mkeywords.get("m_reckw");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < reckw.size(); j++)
			{
				if (words.get(i).equals(reckw.get(j)))
				{
					mmaybe = "";
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
		
		List <String> names = mkeywords.get("mkw_names");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < names.size(); j++)
			{
				if (words.get(i).equals(names.get(j)))
				{
					mmaybe = words.get(i);
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
		
		List <String> genres = mkeywords.get("allm_genres");
		
		for (int i = 0; i < words.size(); i++)
		{
			for (int j = 0; j < genres.size(); j++)
			{
				if (words.get(i).equals(genres.get(j)))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	// finds a genre keyword (or notfound) in the query and sets mrequested to that genre
	public void whatgenre(String query)
	{
		String str[] = query.split(" ");
		List <String> words = new ArrayList <String> ();
		words = Arrays.asList(str);
		
		List <String> genres = mkeywords.get("allm_genres");
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
		mrequested = genre;
		if (mrequested.equals("genrenotfound"))
			mmaybe = "titlenotfound";
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
		
		List <String> names = mnames.get("all");
		
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
	
	public void findmanga(String query)
	{
		String name = gettitle(query);
		
		List <String> given = mnames.get("given");
		List <String> yona = mnames.get("yona");
		List <String> law = mnames.get("law");
		List <String> tcf = mnames.get("tcf");
		List <String> tnf = mnames.get("titlenotfound");
		List <String> gnf = mnames.get("genrenotfound");
		
		for (int i = 0; i < tnf.size(); i++)
		{
			if (name.equals(tnf.get(i)))
				mmaybe = "titlenotfound";
		}
		for (int i = 0; i < gnf.size(); i++)
		{
			if (name.equals(gnf.get(i)))
				mmaybe = "genrenotfound";
		}
		for (int i = 0; i < given.size(); i++)
		{
			if (name.equals(given.get(i)))
				mmaybe = "given";
		}
		for (int i = 0; i < yona.size(); i++)
		{
			if (name.equals(yona.get(i)))
				mmaybe = "yona";
		}
		for (int i = 0; i < law.size(); i++)
		{
			if (name.equals(law.get(i)))
				mmaybe = "law";
		}
		for (int i = 0; i < tcf.size(); i++)
		{
			if (name.equals(tcf.get(i)))
				mmaybe = "tcf";
		}
	}
	
	// randomly finds a manga of the requested genre and resets the value of mrequested
	public String mangarec()
	{
		List <String> avail = mgenres.get(mrequested);
		int rand = (int) (Math.random()*avail.size());
		mrequested = "";
		mmaybe = avail.get(rand);
		return mmaybe;
	}

}
