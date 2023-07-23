package main

import (
	"awesomeProject/responses"
	"encoding/json"
	"fmt"
	"testing"
)

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

func TestSendttpPostSignUpInvalid(t *testing.T) {
	signUp := UserSignUp{
		Login:      "loginnotexists",
		Password:   "123",
		LastName:   "123",
		Patronymic: "123",
		Name:       "123",
	}
	result := sendSignUp(signUp)

	jsonData, err := json.Marshal(result)
	if err != nil {
		fmt.Println("Error marshaling data:", err)

	}
	var errorDetails responses.ErrorDetails
	err = json.Unmarshal(jsonData, &errorDetails)
	if err != nil {
		fmt.Println("Error unmarshaling data:", err)
		return
	}
	if errorDetails.Code != 400 {
		t.Error("Already  exists")
	}

	if errorDetails.Message != "User loginnotexists already exists" {
		t.Error("Already  exists")
	}
}
