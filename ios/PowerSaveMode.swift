//
//  PowerSaveModeModule.swift
//
//  Created by Mahmoud Eldawy on 18/06/2024.
//

import Foundation
import React

@objc(PowerSaveMode)
class PowerSaveMode: RCTEventEmitter {
    
    override init() {
        super.init()
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handlePowerModeChange),
            name: NSNotification.Name.NSProcessInfoPowerStateDidChange,
            object: nil
        )
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }

    override func supportedEvents() -> [String]! {
        return ["PowerSaveModeChanged"]
    }
    
    @objc
    func isPowerSaveModeEnabled(_ callback: RCTResponseSenderBlock) {
        let isLowPowerModeEnabled = ProcessInfo.processInfo.isLowPowerModeEnabled
        callback([NSNull(), isLowPowerModeEnabled])
    }
    
    @objc
    private func handlePowerModeChange() {
        let isLowPowerModeEnabled = ProcessInfo.processInfo.isLowPowerModeEnabled
        sendEvent(withName: "PowerSaveModeChanged", body: ["isPowerSaveMode": isLowPowerModeEnabled])
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
