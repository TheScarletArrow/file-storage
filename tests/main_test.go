package main

import "testing"

func TestSendHttpPostCredsValid(t *testing.T) {
	result := sendHttpPost("adyurkov", "1234")
	if result.AccessToken != "" {
	} else {
		t.Error("Wrong credentials")
	}

}
func TestSendHttpPostCredsInvalid(t *testing.T) {
	result := sendHttpPost("adyurkov", "12345")
	if result.AccessToken == "" {
	} else {
		t.Error("Wrong credentials")
	}

}
