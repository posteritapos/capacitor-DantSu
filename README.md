# capacitor-dantsu

Capacitor plugin for ESC/POS thermal printing

## Install

To use npm

```bash
npm install capacitor-dantsu
````

To use yarn

```bash
yarn add capacitor-dantsu
```

Sync native files

```bash
npx cap sync
```

## API

<docgen-index>

* [`getBluetoothDevices()`](#getbluetoothdevices)
* [`getUsbDevices()`](#getusbdevices)
* [`printFormattedText(...)`](#printformattedtext)
* [`printFormattedTextAndCut(...)`](#printformattedtextandcut)
* [`printFormattedTextAndOpenCashBox(...)`](#printformattedtextandopencashbox)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getBluetoothDevices()

```typescript
getBluetoothDevices() => Promise<{ devices: BluetoothDevice[]; }>
```

Returns paired Bluetooth devices (Android only).
Requires BLUETOOTH / BLUETOOTH_CONNECT permission.

**Returns:** <code>Promise&lt;{ devices: BluetoothDevice[]; }&gt;</code>

--------------------


### getUsbDevices()

```typescript
getUsbDevices() => Promise<{ devices: UsbDevice[]; }>
```

Returns connected USB devices (Android only).
Requires USB host feature.

**Returns:** <code>Promise&lt;{ devices: UsbDevice[]; }&gt;</code>

--------------------


### printFormattedText(...)

```typescript
printFormattedText(options: PrintOptions) => Promise<void>
```

Print formatted text on a thermal printer.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#printoptions">PrintOptions</a></code> |

--------------------


### printFormattedTextAndCut(...)

```typescript
printFormattedTextAndCut(options: PrintOptions) => Promise<void>
```

Print formatted text and cut paper.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#printoptions">PrintOptions</a></code> |

--------------------


### printFormattedTextAndOpenCashBox(...)

```typescript
printFormattedTextAndOpenCashBox(options: PrintOptions) => Promise<void>
```

Print formatted text, cut paper, and open cash box.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#printoptions">PrintOptions</a></code> |

--------------------


### Interfaces


#### BluetoothDevice

Represents a paired/available Bluetooth printer device.

| Prop          | Type                | Description |
| ------------- | ------------------- | ----------- |
| **`name`**    | <code>string</code> | Device name |
| **`address`** | <code>string</code> | MAC address |


#### UsbDevice

Represents an available USB printer device.

| Prop            | Type                | Description    |
| --------------- | ------------------- | -------------- |
| **`vendorId`**  | <code>number</code> | USB vendor ID  |
| **`productId`** | <code>number</code> | USB product ID |
| **`name`**      | <code>string</code> | Device name    |


#### PrintOptions

Options for a print job.

| Prop                              | Type                                                                          | Description                                                                                                                          |
| --------------------------------- | ----------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| **`connection`**                  | <code><a href="#printerconnectionoptions">PrinterConnectionOptions</a></code> | Connection configuration                                                                                                             |
| **`text`**                        | <code>string</code>                                                           | Formatted text to print using DantSu ESC/POS text parser syntax. Supports tags for alignment, bold, images, barcodes, QR codes, etc. |
| **`printerDpi`**                  | <code>number</code>                                                           | Printer DPI (default: 203)                                                                                                           |
| **`printerWidthMM`**              | <code>number</code>                                                           | Paper width in millimeters (default: 80)                                                                                             |
| **`printerNbrCharactersPerLine`** | <code>number</code>                                                           | Number of characters per line (default: 32)                                                                                          |
| **`feedPaperMM`**                 | <code>number</code>                                                           | Feed paper by this many millimeters after printing (default: 20)                                                                     |


#### BluetoothPrinterOptions

Bluetooth connection configuration.

| Prop          | Type                     | Description                          |
| ------------- | ------------------------ | ------------------------------------ |
| **`type`**    | <code>'bluetooth'</code> |                                      |
| **`address`** | <code>string</code>      | MAC address of the Bluetooth printer |


#### TcpPrinterOptions

TCP/IP connection configuration.

| Prop          | Type                | Description                                      |
| ------------- | ------------------- | ------------------------------------------------ |
| **`type`**    | <code>'tcp'</code>  |                                                  |
| **`address`** | <code>string</code> | IP address of the printer                        |
| **`port`**    | <code>number</code> | TCP port (typically 9100)                        |
| **`timeout`** | <code>number</code> | Connection timeout in milliseconds (default: 30) |


#### UsbPrinterOptions

USB connection configuration.

| Prop            | Type                | Description    |
| --------------- | ------------------- | -------------- |
| **`type`**      | <code>'usb'</code>  |                |
| **`vendorId`**  | <code>number</code> | USB vendor ID  |
| **`productId`** | <code>number</code> | USB product ID |


### Type Aliases


#### PrinterConnectionOptions

<code><a href="#bluetoothprinteroptions">BluetoothPrinterOptions</a> | <a href="#tcpprinteroptions">TcpPrinterOptions</a> | <a href="#usbprinteroptions">UsbPrinterOptions</a></code>

</docgen-api>
