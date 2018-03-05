package advertisements;

public class OnlineComputerAdvertisement extends ComputerAdvertisement
{
    public OnlineComputerAdvertisement(String computerType, double cpuSpeed, int cpuCores, boolean includesGPUCard)
    {
        super(computerType, cpuSpeed, cpuCores, includesGPUCard);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }
        else if (this == other)
        {
            return true;
        }
        else if (!other.getClass().equals(this.getClass()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
