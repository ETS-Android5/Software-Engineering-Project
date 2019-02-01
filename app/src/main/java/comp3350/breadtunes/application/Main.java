package comp3350.breadtunes.application;

import comp3350.breadtunes.presentation.CLI;

public class Main
{
	public static final String dbName="SC";

	public static void main(String[] args)
	{
		CLI.run();
		System.out.println("All done");
	}

}
