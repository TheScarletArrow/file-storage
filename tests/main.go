package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

type Tokens struct {
	AccessToken  string `json:"access_token"`
	RefreshToken string `json:"refresh_token"`
}

func sendHttpPost(login string, password string) *Tokens {
	myUrl := "http://localhost:8080/signin"

	user := UserSignIn{
		Username: login,
		Password: password,
	}

	// Create a new HTTP client
	client := &http.Client{}

	// Send the POST request
	marshal, err2 := json.Marshal(user)
	if err2 != nil {
		fmt.Println("Error marshaling JSON:", err2)
		return nil
	}
	response, err := client.Post(myUrl, "application/json",
		bytes.NewBuffer(marshal))
	if err != nil {
		fmt.Println("Error sending POST request:", err)
		//t.Error("Error sending POST request", err)
	}

	defer response.Body.Close()

	// Read the response body
	var tokens Tokens
	err = json.NewDecoder(response.Body).Decode(&tokens)
	if tokens.AccessToken == "" || tokens.RefreshToken == "" {
		//t.Error("Expected non-nil value for tokens, got nil")
	}

	// Print the response
	return &tokens
}
func sendSignUp(signUp UserSignUp) interface{} {
	myUrl := "http://localhost:8080/users/"

	client := &http.Client{}

	marshal, err2 := json.Marshal(signUp)
	if err2 != nil {
		fmt.Println("Error marshaling JSON:", err2)
		return nil
	}
	response, err := client.Post(myUrl, "application/json",
		bytes.NewBuffer(marshal))
	if err != nil {
		fmt.Println("Error sending POST request:", err)
		//t.Error("Error sending POST request", err)
	}

	defer response.Body.Close()
	bodyBytes, err := ioutil.ReadAll(response.Body)

	var jsonData interface{}
	err3 := json.Unmarshal(bodyBytes, &jsonData)
	if err3 != nil {
		return nil
	}
	return jsonData
}
