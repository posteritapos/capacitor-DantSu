import { WebPlugin } from '@capacitor/core';

import type { BluetoothDevice, DantSuPlugin, PrintOptions, UsbDevice } from './definitions';

export class DantSuWeb extends WebPlugin implements DantSuPlugin {
  async getBluetoothDevices(): Promise<{ devices: BluetoothDevice[] }> {
    throw this.unimplemented('getBluetoothDevices is not available on web.');
  }

  async getUsbDevices(): Promise<{ devices: UsbDevice[] }> {
    throw this.unimplemented('getUsbDevices is not available on web.');
  }

  async printFormattedText(_options: PrintOptions): Promise<void> {
    throw this.unimplemented('printFormattedText is not available on web.');
  }

  async printFormattedTextAndCut(_options: PrintOptions): Promise<void> {
    throw this.unimplemented('printFormattedTextAndCut is not available on web.');
  }

  async printFormattedTextAndOpenCashBox(_options: PrintOptions): Promise<void> {
    throw this.unimplemented('printFormattedTextAndOpenCashBox is not available on web.');
  }
}
