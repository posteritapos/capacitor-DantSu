package com.posteritapos.plugins.dantsu;

import android.bluetooth.BluetoothDevice;
import android.hardware.usb.UsbDevice;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.List;

@CapacitorPlugin(name = "DantSu")
public class DantSuPlugin extends Plugin {

    private final DantSu implementation = new DantSu();

    @PluginMethod
    public void getBluetoothDevices(PluginCall call) {
        List<BluetoothDevice> devices = implementation.getBluetoothDevices();
        JSArray devicesArray = new JSArray();
        for (BluetoothDevice device : devices) {
            JSObject d = new JSObject();
            d.put("name", device.getName() != null ? device.getName() : "");
            d.put("address", device.getAddress());
            devicesArray.put(d);
        }
        JSObject ret = new JSObject();
        ret.put("devices", devicesArray);
        call.resolve(ret);
    }

    @PluginMethod
    public void getUsbDevices(PluginCall call) {
        List<UsbDevice> devices = implementation.getUsbDevices(getContext());
        JSArray devicesArray = new JSArray();
        for (UsbDevice device : devices) {
            JSObject d = new JSObject();
            d.put("vendorId", device.getVendorId());
            d.put("productId", device.getProductId());
            d.put("name", device.getDeviceName() != null ? device.getDeviceName() : "");
            devicesArray.put(d);
        }
        JSObject ret = new JSObject();
        ret.put("devices", devicesArray);
        call.resolve(ret);
    }

    @PluginMethod
    public void printFormattedText(PluginCall call) {
        executePrint(call, DantSu.PRINT_MODE_NORMAL);
    }

    @PluginMethod
    public void printFormattedTextAndCut(PluginCall call) {
        executePrint(call, DantSu.PRINT_MODE_CUT);
    }

    @PluginMethod
    public void printFormattedTextAndOpenCashBox(PluginCall call) {
        executePrint(call, DantSu.PRINT_MODE_CUT_AND_OPEN_CASH_BOX);
    }

    private void executePrint(PluginCall call, String mode) {
        JSObject connection = call.getObject("connection");
        if (connection == null) {
            call.reject("Missing required parameter: connection");
            return;
        }

        String text = call.getString("text");
        if (text == null || text.isEmpty()) {
            call.reject("Missing required parameter: text");
            return;
        }

        String connectionType = connection.getString("type", "bluetooth");
        int printerDpi = call.getInt("printerDpi", 203);
        float printerWidthMM = call.getFloat("printerWidthMM", 80.0f);
        int printerNbrCharactersPerLine = call.getInt("printerNbrCharactersPerLine", 32);
        float feedPaperMM = call.getFloat("feedPaperMM", 20.0f);

        String address = connection.getString("address", "");
        int port = connection.getInteger("port", 9100);
        int timeout = connection.getInteger("timeout", 30);
        int vendorId = connection.getInteger("vendorId", 0);
        int productId = connection.getInteger("productId", 0);

        new Thread(() -> {
            try {
                implementation.print(
                    getContext(),
                    connectionType,
                    address,
                    port,
                    timeout,
                    vendorId,
                    productId,
                    text,
                    printerDpi,
                    printerWidthMM,
                    printerNbrCharactersPerLine,
                    feedPaperMM,
                    mode
                );
                call.resolve();
            } catch (EscPosConnectionException e) {
                call.reject("CONNECTION_ERROR", e.getMessage(), e);
            } catch (EscPosParserException e) {
                call.reject("PARSER_ERROR", e.getMessage(), e);
            } catch (EscPosEncodingException e) {
                call.reject("ENCODING_ERROR", e.getMessage(), e);
            } catch (EscPosBarcodeException e) {
                call.reject("BARCODE_ERROR", e.getMessage(), e);
            }
        })
            .start();
    }
}
