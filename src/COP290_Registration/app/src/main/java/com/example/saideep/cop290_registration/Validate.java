package com.example.saideep.cop290_registration;

/**
 * Created by saideep on 18/1/16.
 *
 *
 * This class contains methods to Validate the NAME & ENTRY_NUMBER text fields entered by the user
 */

public class Validate
{

    /**
     *  Validating the NAME fields
     *  @param name : Name entered by the user
     *  @return  true if the input string consists only letters and spaces (leading or trailing space allowed)
     *  Regex source : http://stackoverflow.com/questions/27126455/java-regex-to-validate-full-name-allow-only-spaces-letters-commas-dots-in-java
     */
    public  static boolean ValidateName(String name)
    {
        return name.matches("[a-zA-Z]+( +[a-zA-Z]+)*"); // Regex to accept only alphabets and spaces and disallowing spaces at the beginning and the end of the string
    }



    /**
     * Code for Validating ENTRY_NUMBER fields follows
     *
     * Assumptions : COP290 is an Undergraduate course which means PG entry numbers are invalid
     *              A lower limit of 2005 & an upper limit of 2014 for the year of entry (**2015 entry students didn't meet the prerequisite yet)
     */

    //UG Courses that are part of the 2012 or prior Curriculum
    public static String[] courses_2012_or_prior = {"BB5","CH1","CH7","CS1","CS5","CE1","EE1","EE2","EE5","ME1","ME2","MT5","PH1","TT1"};

    //UG Courses that are part of the 2013 Curriculum
    public static String[] courses_2013 = {"BB5","CH1","CH7","CS1","CS5","CE1","EE1","EE3","ME1","ME2","MT6","PH1","TT1"};

    //UG Courses that are part of the 2014 Curriculum
    public static String[] courses_2014 = {"BB1","BB5","CH1","CH7","CS1","CS5","CE1","EE1","EE3","ME1","ME2","MT1","MT6","PH1","TT1"};

    // We are not concerned about PG programs as COP290 is an undergraduate course


    /**
     * Helper function to check if a particular branch is valid or not
     * @param branch  (e.g BB5,CS1 etc )
     * @param year    - year of entry( 20XX )
     * It checks whether a branch belongs to the corresponding year's curriculum or not
     * @return true if it does
     */

    public static boolean contains(String branch,Integer year)
    {

        if(year<=2012)    // if curriculum is 2012 or prior
        {
            /**
             *  Linear search to check if a branch belongs to the curriculum or not
             */

            for (int i = 0; i < courses_2012_or_prior.length; i++)
            {
                if (courses_2012_or_prior[i].compareTo(branch) == 0)
                    return true;
            }
            return false;
        }

        else if(year==2013) // if 2013 curriculum
        {
            for (int i = 0; i < courses_2013.length; i++)
            {
                if (courses_2013[i].compareTo(branch) == 0)
                    return true;
            }
            return false;
        }
        else if(year==2014) // if 2014 curriculum
        {
            for (int i = 0; i < courses_2014.length; i++)
            {
                if (courses_2014[i].compareTo(branch) == 0)
                    return true;
            }
            return false;
        }
        return false;
    }


    /**
     * Method to validate entry number entered by user
     * @param entry the string entered by the user in the ENTRY_NUMBER field
     * @return  true if the entry number is a valid one
     *
     * Conditions for a valid entry number :
     *
     * Length of a Valid Entry number is 11
     * First 4 characters (year) has to be a Valid Year (i.e 2005-2014)
     * Next  3 characters (branch) has to be a Valid Programme (i.e  contains(branch,year)==true)
     * Next  4 characters has to be a Valid Number (All digits)
     */

    public static boolean ValidateEntry(String entry)
    {
        if (entry.length() != 11) return false;

        try{

            int n = Integer.parseInt(entry.substring(0,4));

            if (n >= 2005 && n <= 2014)
            {
                String branch = entry.substring(4,7);

                if (contains(branch.toUpperCase(),n))
                {
                    try
                    {
                        n = Integer.parseInt(entry.substring(7));
                        return true;
                    }
                    catch (NumberFormatException e){
                        return false;
                    }
                }

                return false;
            }

            return false;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

}
