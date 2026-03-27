package com.posteritapos.plugins.dantsu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DantSu {

    public static final String PRINT_MODE_NORMAL = "normal";
    public static final String PRINT_MODE_CUT = "cut";
    public static final String PRINT_MODE_CUT_AND_OPEN_CASH_BOX = "cutAndOpenCashBox";

    /**
     * Returns a list of paired Bluetooth devices.
     */
    public List<BluetoothDevice> getBluetoothDevices() {
        List<BluetoothDevice> result = new ArrayList<>();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null || !adapter.isEnabled()) {
            return result;
        }
        Set<BluetoothDevice> paired = adapter.getBondedDevices();
        if (paired != null) {
            result.addAll(paired);
        }
        return result;
    }

    /**
     * Returns a list of connected USB devices that may be printers.
     */
    public List<UsbDevice> getUsbDevices(Context context) {
        List<UsbDevice> result = new ArrayList<>();
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        if (usbManager == null) {
            return result;
        }
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            result.add(device);
        }
        return result;
    }

    /**
     * Prints formatted text on a thermal printer.
     *
     * @param context        Android Context
     * @param connectionType "bluetooth", "tcp", or "usb"
     * @param address        MAC address (Bluetooth) or IP address (TCP)
     * @param port           TCP port (TCP only, default 9100)
     * @param timeout        TCP timeout in ms (TCP only, default 30)
     * @param vendorId       USB vendor ID (USB only)
     * @param productId      USB product ID (USB only)
     * @param text           Formatted ESC/POS text
     * @param printerDpi     Printer DPI (default 203)
     * @param printerWidthMM Paper width in mm (default 80)
     * @param printerNbrCharactersPerLine Characters per line (default 32)
     * @param feedPaperMM    Feed paper mm after print (default 20)
     * @param mode           PRINT_MODE_NORMAL, PRINT_MODE_CUT, or PRINT_MODE_CUT_AND_OPEN_CASH_BOX
     */
    public void print(
        Context context,
        String connectionType,
        String address,
        int port,
        int timeout,
        int vendorId,
        int productId,
        String text,
        int printerDpi,
        float printerWidthMM,
        int printerNbrCharactersPerLine,
        float feedPaperMM,
        String mode
    ) throws EscPosConnectionException, EscPosParserException, EscPosEncodingException, EscPosBarcodeException {
        EscPosPrinter printer;

        switch (connectionType) {
            case "tcp":
                TcpConnection tcpConnection = new TcpConnection(address, port, timeout);
                printer = new EscPosPrinter(tcpConnection, printerDpi, printerWidthMM, printerNbrCharactersPerLine);
                break;
            case "usb":
                UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
                if (usbManager == null) {
                    throw new EscPosConnectionException("UsbManager is not available.");
                }
                UsbDevice usbDevice = findUsbDevice(usbManager, vendorId, productId);
                if (usbDevice == null) {
                    throw new EscPosConnectionException("USB device not found (vendorId=" + vendorId + ", productId=" + productId + ").");
                }
                UsbConnection usbConnection = new UsbConnection(usbManager, usbDevice);
                printer = new EscPosPrinter(usbConnection, printerDpi, printerWidthMM, printerNbrCharactersPerLine);
                break;
            case "bluetooth":
            default:
                BluetoothConnection bluetoothConnection = findBluetoothDevice(address);
                if (bluetoothConnection == null) {
                    throw new EscPosConnectionException("Bluetooth device not found: " + address);
                }
                printer = new EscPosPrinter(bluetoothConnection, printerDpi, printerWidthMM, printerNbrCharactersPerLine);
                break;
        }

        switch (mode) {
            case PRINT_MODE_CUT:
                printer.printFormattedTextAndCut(text, feedPaperMM);
                break;
            case PRINT_MODE_CUT_AND_OPEN_CASH_BOX:
                printer.printFormattedTextAndOpenCashBox(text, feedPaperMM);
                break;
            default:
                printer.printFormattedText(text, feedPaperMM);
                break;
        }

        printer.disconnectPrinter();
    }

    private BluetoothConnection findBluetoothDevice(String address) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null || !adapter.isEnabled()) {
            return null;
        }
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        if (devices == null) {
            return null;
        }
        for (BluetoothDevice device : devices) {
            if (device.getAddress().equalsIgnoreCase(address)) {
                return new BluetoothConnection(device);
            }
        }
        return null;
    }

    private UsbDevice findUsbDevice(UsbManager usbManager, int vendorId, int productId) {
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            if (device.getVendorId() == vendorId && device.getProductId() == productId) {
                return device;
            }
        }
        return null;
    }
}
