/**
 * Represents a paired/available Bluetooth printer device.
 */
export interface BluetoothDevice {
  /** Device name */
  name: string;
  /** MAC address */
  address: string;
}

/**
 * Represents an available USB printer device.
 */
export interface UsbDevice {
  /** USB vendor ID */
  vendorId: number;
  /** USB product ID */
  productId: number;
  /** Device name */
  name: string;
}

/**
 * Bluetooth connection configuration.
 */
export interface BluetoothPrinterOptions {
  type: 'bluetooth';
  /** MAC address of the Bluetooth printer */
  address: string;
}

/**
 * TCP/IP connection configuration.
 */
export interface TcpPrinterOptions {
  type: 'tcp';
  /** IP address of the printer */
  address: string;
  /** TCP port (typically 9100) */
  port: number;
  /** Connection timeout in milliseconds (default: 30) */
  timeout?: number;
}

/**
 * USB connection configuration.
 */
export interface UsbPrinterOptions {
  type: 'usb';
  /** USB vendor ID */
  vendorId: number;
  /** USB product ID */
  productId: number;
}

export type PrinterConnectionOptions = BluetoothPrinterOptions | TcpPrinterOptions | UsbPrinterOptions;

/**
 * Options for a print job.
 */
export interface PrintOptions {
  /** Connection configuration */
  connection: PrinterConnectionOptions;
  /**
   * Formatted text to print using DantSu ESC/POS text parser syntax.
   * Supports tags for alignment, bold, images, barcodes, QR codes, etc.
   */
  text: string;
  /** Printer DPI (default: 203) */
  printerDpi?: number;
  /** Paper width in millimeters (default: 80) */
  printerWidthMM?: number;
  /** Number of characters per line (default: 32) */
  printerNbrCharactersPerLine?: number;
  /** Feed paper by this many millimeters after printing (default: 20) */
  feedPaperMM?: number;
}

export interface DantSuPlugin {
  /**
   * Returns paired Bluetooth devices (Android only).
   * Requires BLUETOOTH / BLUETOOTH_CONNECT permission.
   */
  getBluetoothDevices(): Promise<{ devices: BluetoothDevice[] }>;

  /**
   * Returns connected USB devices (Android only).
   * Requires USB host feature.
   */
  getUsbDevices(): Promise<{ devices: UsbDevice[] }>;

  /**
   * Print formatted text on a thermal printer.
   */
  printFormattedText(options: PrintOptions): Promise<void>;

  /**
   * Print formatted text and cut paper.
   */
  printFormattedTextAndCut(options: PrintOptions): Promise<void>;

  /**
   * Print formatted text, cut paper, and open cash box.
   */
  printFormattedTextAndOpenCashBox(options: PrintOptions): Promise<void>;
}
