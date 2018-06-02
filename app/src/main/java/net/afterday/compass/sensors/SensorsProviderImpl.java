package net.afterday.compass.sensors;

import android.content.Context;

import net.afterday.compass.sensors.Battery.Battery;
import net.afterday.compass.sensors.Battery.BatteryImpl;
import net.afterday.compass.sensors.Bluetooth.Bluetooth;
import net.afterday.compass.sensors.Bluetooth.BluetoothImpl;
import net.afterday.compass.sensors.WiFi.WiFi;
import net.afterday.compass.sensors.WiFi.WifiImpl;

/**
 * Created by Justas Spakauskas on 2/7/2018.
 */

public class SensorsProviderImpl implements SensorsProvider
{
    private static SensorsProvider instance;
    private Context context;
    private SensorsProviderImpl(Context context)
    {
        this.context = context;
    }

    public static SensorsProvider initialize(Context context)
    {
        if(instance == null)
        {
            instance = new SensorsProviderImpl(context);
        }
        return instance;
    }

    public static SensorsProvider instance()
    {
        if(instance == null)
        {
            throw new IllegalStateException("Sensors provider not initialized");
        }
        return instance;
    }

    public WiFi getWifiSensor()
    {
        return new WifiImpl(context);
    }

    @Override
    public Battery getBatterySensor()
    {
        return new BatteryImpl(context);
    }

    @Override
    public Bluetooth getBluetoothSensor()
    {
        return new BluetoothImpl(context);
    }

}
