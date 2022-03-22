package com.juxtopia.cammradtour;

import com.google.zxing.Result;

public interface findingQrCode {
    void onQRCodeFound(Result qrCode);
    void qrCodeNotFound();
}
