package chatbot;
import java.util.*;


public class Driver // This is a (mostly) hardcoded/prerun question list for the driver; Chatbot is interactive
{
	public static void driver(List <String> q)
	{
		Games g = new Games();
		Manga m = new Manga();
		Anime a = new Anime();
		Domain d = new Domain(g, m, a);
		
		String query = "";
		Domain v;
		
		Map <String, Domain> domains = new LinkedHashMap <String, Domain> ();
		domains.put("general", d);
		domains.put("games", g);
		domains.put("manga", m);
		domains.put("anime", a);
		
		System.out.println("Hi, I'm DiscountSiri! You can ask me about: games, manga, and anime.\n");
		
		for (int i = 0; i < q.size(); i++)
		{
			d.clearresponse();
			System.out.println("User: " + q.get(i));
			query = q.get(i).toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");
			d.findkws(query);
			v = d.determineDomain(domains);
			v.findTopic(query);
			System.out.println("Bot: " + d.respond());
		}
	}
	
	public static void main (String [] args)
	{
		System.out.println("\n-------------- Conversation 1 --------------");
		List <String> questions = new ArrayList <String> (Arrays.asList(
				                  "Hi",
								  "Recommend me a MOBA", 
				                  "What's it about?", 
				                  "How do you play?",
				                  "Okay, thank you, bye!"));

		driver(questions);
		
		System.out.println("\n-------------- Conversation 2 --------------");
		questions = (Arrays.asList("Hi",
								"Give me something shoujo", 
								// type anime here
								"WHat's that about",
								"How long is it?",
								"Would you recommend a manga too?",
                				"Thanks, bye!"));
		
		driver(questions);
		
		System.out.println("\n-------------- Conversation 3 --------------");
		questions = (Arrays.asList("Hello. Good morning",
								"Recommend me a manga please",
								"shoujo", 
								"what's it about",
								"how much is completed",
								"ok ty bye"));
		
		driver(questions);
		
	}
}