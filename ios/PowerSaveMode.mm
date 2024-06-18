//
//  PowerSaveMode.m
//
//   Created by Mahmoud Eldawy on 18/06/2024.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(PowerSaveMode, RCTEventEmitter)

RCT_EXTERN_METHOD(isPowerSaveModeEnabled:(RCTResponseSenderBlock)callback)

@end
