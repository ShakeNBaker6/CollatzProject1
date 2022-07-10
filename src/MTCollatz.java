import java.time.Instant;

public class MTCollatz {
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("Missing necessary arguments");
            return;
        }

        if(!tryParseInt(args[0]) && !tryParseInt(args[1]))
        {
            System.out.println("Not a proper integer value was given");
            return;
        }

        int numberOfCalculations = Integer.parseInt(args[0]);

        int numberOfThreads = Integer.parseInt(args[1]);

        int[] stoppingTimes = new int[1000];

        Instant startTime = Instant.now();
        for(long i = 1; i < numberOfCalculations; i++)
        {
            long a = i;
            int stoppingTime = 1;
            while (a != 1)
            {
                a = CalculateNextA(a);
                stoppingTime ++;
            }
            stoppingTimes[stoppingTime] = stoppingTimes[stoppingTime] + 1;
        }
        Instant endTime = Instant.now();
        double elapsedTime = ((double) endTime.toEpochMilli() - (double) startTime.toEpochMilli())/1000.0;

        System.err.print(numberOfCalculations);
        System.err.print(",");
        System.err.print(numberOfThreads);
        System.err.print(",");
        System.err.println(elapsedTime);

        for(int i = 0; i < stoppingTimes.length; i++)
        {
            System.out.print(i);
            System.out.print(",");
            System.out.println(stoppingTimes[i]);
        }
    }

    private static long CalculateNextA(long a)
    {
        long nextA;
        if(a % 2 == 0)
        {
            nextA = a / 2;
        }
        else
        {
            nextA = (3*a) + 1;
        }
        return nextA;
    }

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
