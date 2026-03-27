import { WebPlugin } from '@capacitor/core';

import type { BluetoothDevice, DantSuPlugin, PrintOptions, UsbDevice } from './definitions';

const DUMMY_BLUETOOTH_DEVICES: BluetoothDevice[] = [
  { name: 'Dummy Bluetooth Printer 1', address: 'AA:BB:CC:DD:EE:01' },
  { name: 'Dummy Bluetooth Printer 2', address: 'AA:BB:CC:DD:EE:02' },
];

const DUMMY_USB_DEVICES: UsbDevice[] = [
  { vendorId: 0x04b8, productId: 0x0202, name: 'Dummy USB Printer 1' },
  { vendorId: 0x0519, productId: 0x0003, name: 'Dummy USB Printer 2' },
];

export class DantSuWeb extends WebPlugin implements DantSuPlugin {
  async getBluetoothDevices(): Promise<{ devices: BluetoothDevice[] }> {
    console.log('[DantSu] getBluetoothDevices (web stub) → returning dummy devices:', DUMMY_BLUETOOTH_DEVICES);
    return { devices: DUMMY_BLUETOOTH_DEVICES };
  }

  async getUsbDevices(): Promise<{ devices: UsbDevice[] }> {
    console.log('[DantSu] getUsbDevices (web stub) → returning dummy devices:', DUMMY_USB_DEVICES);
    return { devices: DUMMY_USB_DEVICES };
  }

  async printFormattedText(options: PrintOptions): Promise<void> {
    console.log('[DantSu] printFormattedText (web stub)', {
      connection: options.connection,
      text: options.text,
      printerDpi: options.printerDpi ?? 203,
      printerWidthMM: options.printerWidthMM ?? 80,
      printerNbrCharactersPerLine: options.printerNbrCharactersPerLine ?? 32,
      feedPaperMM: options.feedPaperMM ?? 20,
    });
  }

  async printFormattedTextAndCut(options: PrintOptions): Promise<void> {
    console.log('[DantSu] printFormattedTextAndCut (web stub)', {
      connection: options.connection,
      text: options.text,
      printerDpi: options.printerDpi ?? 203,
      printerWidthMM: options.printerWidthMM ?? 80,
      printerNbrCharactersPerLine: options.printerNbrCharactersPerLine ?? 32,
      feedPaperMM: options.feedPaperMM ?? 20,
      cut: true,
    });
  }

  async printFormattedTextAndOpenCashBox(options: PrintOptions): Promise<void> {
    console.log('[DantSu] printFormattedTextAndOpenCashBox (web stub)', {
      connection: options.connection,
      text: options.text,
      printerDpi: options.printerDpi ?? 203,
      printerWidthMM: options.printerWidthMM ?? 80,
      printerNbrCharactersPerLine: options.printerNbrCharactersPerLine ?? 32,
      feedPaperMM: options.feedPaperMM ?? 20,
      cut: true,
      openCashBox: true,
    });
  }
}
