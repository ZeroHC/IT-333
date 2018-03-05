package advertisements;

import java.util.Objects;

public class ComputerAdvertisement
{
    //simple fields
    private String computerType;//laptop, desktop, tablet,...
    private double cpuSpeed; //ghz
    private int cpuCores;
    private boolean includesGPUCard;

    public ComputerAdvertisement(String computerType, double cpuSpeed, int cpuCores, boolean includesGPUCard)
    {
        this.computerType = computerType;
        this.cpuSpeed = cpuSpeed;
        this.cpuCores = cpuCores;
        this.includesGPUCard = includesGPUCard;
    }

    public String getComputerType()
    {
        return computerType;
    }

    public void setComputerType(String computerType)
    {
        this.computerType = computerType;
    }

    public double getCpuSpeed()
    {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed)
    {
        this.cpuSpeed = cpuSpeed;
    }

    public int getCpuCores()
    {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores)
    {
        this.cpuCores = cpuCores;
    }

    public boolean isIncludesGPUCard()
    {
        return includesGPUCard;
    }

    public void setIncludesGPUCard(boolean includesGPUCard)
    {
        this.includesGPUCard = includesGPUCard;
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
        //this is not reliable and is not symmetric
        /*else if (!(other instanceof ComputerAdvertisement))
        {
            return false;
        }*/
        else
        {
            ComputerAdvertisement otherAd = (ComputerAdvertisement) other;

            return this.computerType.equals(otherAd.computerType)
                    && this.cpuSpeed == otherAd.cpuSpeed
                    && this.cpuCores == otherAd.cpuCores;
        }
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}
