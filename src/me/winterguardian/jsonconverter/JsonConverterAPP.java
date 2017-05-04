package me.winterguardian.jsonconverter;

import java.util.Scanner;

public class JsonConverterAPP
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		boolean error = false;
		
		do
		{
			System.out.println("Please type your minecraft message to convert into Json:");
			
			String message = sc.next() + sc.nextLine();
			message = message.replaceAll("&", "§");
			String hoverEventType = null, hoverEventValue = null, clickEventType = null, clickEventValue = null;
			
			System.out.println("Optional: Do you want to add a hover event ? (Y/N)");
			
			if(sc.next().equalsIgnoreCase("y"))
			{
				System.out.println("Please type the corresponding hover event number: (1-4)");
				System.out.println("1: show_text");
				System.out.println("2: show_item");
				System.out.println("3: show_entity");
				System.out.println("4: show_achievement");
				switch(sc.nextInt())
				{
				case 1:
					hoverEventType = "show_text";
					break;
					
				case 2:
					hoverEventType = "show_item";
					break;
					
				case 3:
					hoverEventType = "show_entity";
					break;
					
				case 4:
					hoverEventType = "show_achievement";
					break;
					
				default:
					hoverEventType = null;
				}
				
				System.out.println("Please type the hover event value: (Some Json...)");
				hoverEventValue = sc.next() + sc.nextLine();
			}
			
			System.out.println("Optional: Do you want to add a click event ? (Y/N)");
			
			if(sc.next().equalsIgnoreCase("y"))
			{
				System.out.println("Please type the corresponding click event number: (1-4)");
				System.out.println("1: run_command");
				System.out.println("2: suggest_command");
				System.out.println("3: open_url");
				System.out.println("4: change_page");
				switch(sc.nextInt())
				{
				case 1:
					clickEventType = "run_command";
					break;
					
				case 2:
					clickEventType = "suggest_command";
					break;
					
				case 3:
					clickEventType = "open_url";
					break;
					
				case 4:
					clickEventType = "change_page";
					break;
					
				default:
					clickEventType = null;
				}
				
				System.out.println("Please type the click event value: (Some Json...)");
				clickEventValue = sc.next() + sc.nextLine();
			}
			
			try
			{
				message = JsonConverter.toJson(message, hoverEventType, hoverEventValue, clickEventType, clickEventValue);
		
				System.out.println("Result:");
				System.out.println(message);
				System.out.println();
				System.out.println("Do you want to continue ? (Y/N)");
			}
			catch(Exception e)
			{
				System.out.println("Sorry but an internal error occured while processing to your request. Please send me this error on spigotmc.net (WinterGuardian)");
				e.printStackTrace();
				error = true;
			}
		}
		while(!error && sc.next().equalsIgnoreCase("y"));
		
		sc.close();
	}
}
