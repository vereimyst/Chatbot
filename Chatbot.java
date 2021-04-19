package chatbot;
import java.util.*;

public class Chatbot
{	
	public static void main (String [] args)
	{
		Games g = new Games();
		Manga m = new Manga();
		Anime a = new Anime();
		Domain d = new Domain(g, m, a);
		Scanner sc = new Scanner (System.in);
		String query = "";
		Domain v;
		
		//String temp = "";
		
	// Since determinDomain takes in a map of string to domain, create it here with my initialized objects
	// This allows me to apply the correct domain as evaluated in determinDomain
		Map <String, Domain> domains = new LinkedHashMap <String, Domain> ();
		domains.put("general", d);
		domains.put("games", g);
		domains.put("manga", m);
		domains.put("anime", a);
		
		System.out.println("Hi, I'm DiscountSiri! You can ask me about: games, manga, and anime.");
		
		while (d.getleave() == false)
		{
			d.clearresponse();
			query = sc.nextLine().toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");
			d.findkws(query);
			v = d.determineDomain(domains);
			v.findTopic(query);
			System.out.println(d.respond());
		}
	}
}

/*
--------------------------------------------------
Chat sample
--------------------------------------------------

User: Hey
Chatbot: Hey there!
User: What movie should I watch?
Chatbot: What genre are you interested in?
User: I'm interested in Thriller
Chatbot: Limitless
User: What is Limitless about?
Chatbot: The movie is about gaining more intelligence using a mystery pill!
User: Who is Mike Myers?
Chatbot: Are you referring to the programming language?
User: Yes, I'm referring to the programming language
Chatbot: It's an object oriented programming language.

--------------------------------------------------
Program workflow
--------------------------------------------------
Loop
1. Get input from console
2. Determine the domain
3. Determine if query is ambiguous
4. find topic(s), clarify if needed
5. Look up knowledge base and produce a response (randomize for multiple responses)
--------------------------------------------------

*/