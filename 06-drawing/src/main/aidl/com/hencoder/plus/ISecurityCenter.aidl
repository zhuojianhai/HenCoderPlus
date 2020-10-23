// ISecurityCenter.aidl
package com.hencoder.plus;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String content);
}
