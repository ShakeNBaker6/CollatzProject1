public class MTCollatz {
    public static void main(String[] args)
    {
        int[] stoppingTimes = new int[1000];
        for(long i = 1; i < 100000; i++)
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

}
