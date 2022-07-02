package com.sokolov.webApplication.soap;

import com.sokolov.webApplication.models.User;
import com.sokolov.webApplication.services.UserService;
import com.sokolov.webApplication.soap.generated.classes.LoginWrapper;
import com.sokolov.webApplication.soap.generated.classes.ObjectFactory;
import com.sokolov.webApplication.soap.generated.classes.OperationSuccess;
import com.sokolov.webApplication.soap.generated.classes.UserWrapper;
import com.sokolov.webApplication.utils.Converter;
import com.sokolov.webApplication.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

@Endpoint
public class UserServiceEndpoint {

    protected static final String NAMESPACE_URI = "http://soap/users";

    protected final Converter<User,
            com.sokolov.webApplication.soap.generated.classes.User> converter;
    protected final UserService userService;

    protected final UserValidator userValidator;

    @Autowired
    public UserServiceEndpoint(Converter<User,
            com.sokolov.webApplication.soap.generated.classes.User> converter, UserService userService, UserValidator userValidator) {
        this.converter = converter;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public JAXBElement<UserWrapper> getAllUsers(
            @RequestPayload JAXBElement<LoginWrapper> request) {
        ObjectFactory factory = new ObjectFactory();
        UserWrapper response = factory.createUserWrapper();
        userService.getAllUsers().stream()
                .map(converter::convertToSoap)
                .forEach(user -> response.getUser().add(user));
        return factory.createGetAllUsersResponse(response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByLoginWithRolesRequest")
    @ResponsePayload
    public JAXBElement<UserWrapper> getUserByLoginWithRoles(
            @RequestPayload JAXBElement<LoginWrapper> request) {
        ObjectFactory factory = new ObjectFactory();
        UserWrapper response = factory.createUserWrapper();
        String login = request.getValue().getLogin().get(0);
        if (login != null) {
            response.getUser().add(converter.convertToSoap(
                    userService.getUserByLoginWithRoles(login)));
        }
        return factory.createGetUserByLoginWithRolesResponse(response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public JAXBElement<OperationSuccess> deleteUserRequest(
            @RequestPayload JAXBElement<LoginWrapper> request) {
        ObjectFactory factory = new ObjectFactory();
        OperationSuccess response = factory.createOperationSuccess();
        String login = request.getValue().getLogin().get(0);
        if (login != null) {
            userService.deleteUser(login);
            response.setSuccess(true);
        }
        return factory.createDeleteUserResponse(response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addNewUserRequest")
    @ResponsePayload
    public JAXBElement<OperationSuccess> addNewUser(
            @RequestPayload JAXBElement<UserWrapper> request) {
        ObjectFactory factory = new ObjectFactory();
        OperationSuccess response = factory.createOperationSuccess();
        com.sokolov.webApplication.soap.generated.classes.User soapUser =
                request.getValue().getUser().get(0);
        if (soapUser != null) {
            User user = converter.convertFromSoap(soapUser);
            if (userValidator.setUser(user).isValid()) {
                userService.addNewUser(user);
                response.setSuccess(true);
            }else {
                response.setSuccess(false);
                response.getErrors().addAll(userValidator.getMessage());
            }
        }
        return factory.createAddNewUserResponse(response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public JAXBElement<OperationSuccess> updateUser(
            @RequestPayload JAXBElement<UserWrapper> request) {
        ObjectFactory factory = new ObjectFactory();
        OperationSuccess response = factory.createOperationSuccess();
        com.sokolov.webApplication.soap.generated.classes.User soapUser =
                request.getValue().getUser().get(0);
        if (soapUser != null) {
            User user = converter.convertFromSoap(soapUser);
            if (userValidator.setUser(user).isValid()) {
                userService.updateUser(user);
                response.setSuccess(true);
            }else {
                response.setSuccess(false);
                response.getErrors().addAll(userValidator.getMessage());
            }
        }
        return factory.createUpdateUserResponse(response);
    }
}