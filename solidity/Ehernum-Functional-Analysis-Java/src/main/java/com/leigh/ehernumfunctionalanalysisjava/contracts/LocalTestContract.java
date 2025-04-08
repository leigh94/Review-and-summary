package com.leigh.ehernumfunctionalanalysisjava.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.4.
 */
@SuppressWarnings("rawtypes")
public class LocalTestContract extends Contract {
    public static final String BINARY = "6080604052348015600e575f80fd5b506104d68061001c5f395ff3fe608060405234801561000f575f80fd5b506004361061003f575f3560e01c8063368b877214610043578063ce6d41de14610058578063e21f37ce14610076575b5f80fd5b6100566100513660046101f2565b61007e565b005b6100606100c4565b60405161006d91906102a5565b60405180910390f35b610060610153565b5f610089828261035e565b507fbb4847942d98bb5bb249692c72ce235605e41502e705831e609875320ef2cac75f6040516100b99190610419565b60405180910390a150565b60605f80546100d2906102da565b80601f01602080910402602001604051908101604052809291908181526020018280546100fe906102da565b80156101495780601f1061012057610100808354040283529160200191610149565b820191905f5260205f20905b81548152906001019060200180831161012c57829003601f168201915b5050505050905090565b5f805461015f906102da565b80601f016020809104026020016040519081016040528092919081815260200182805461018b906102da565b80156101d65780601f106101ad576101008083540402835291602001916101d6565b820191905f5260205f20905b8154815290600101906020018083116101b957829003601f168201915b505050505081565b634e487b7160e01b5f52604160045260245ffd5b5f60208284031215610202575f80fd5b813567ffffffffffffffff811115610218575f80fd5b8201601f81018413610228575f80fd5b803567ffffffffffffffff811115610242576102426101de565b604051601f8201601f19908116603f0116810167ffffffffffffffff81118282101715610271576102716101de565b604052818152828201602001861015610288575f80fd5b816020840160208301375f91810160200191909152949350505050565b602081525f82518060208401528060208501604085015e5f604082850101526040601f19601f83011684010191505092915050565b600181811c908216806102ee57607f821691505b60208210810361030c57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561035957805f5260205f20601f840160051c810160208510156103375750805b601f840160051c820191505b81811015610356575f8155600101610343565b50505b505050565b815167ffffffffffffffff811115610378576103786101de565b61038c8161038684546102da565b84610312565b6020601f8211600181146103be575f83156103a75750848201515b5f19600385901b1c1916600184901b178455610356565b5f84815260208120601f198516915b828110156103ed57878501518255602094850194600190920191016103cd565b508482101561040a57868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b602081525f80835461042a816102da565b806020860152600182165f8114610448576001811461046457610495565b60ff1983166040870152604082151560051b8701019350610495565b865f5260205f205f5b8381101561048c5781548882016040015260019091019060200161046d565b87016040019450505b50919594505050505056fea26469706673582212205d30dcd62db333745c8b50092cc2aa3c70a8e39e236c792b37547107a75e077664736f6c634300081a0033";

    public static final String FUNC_GETMESSAGE = "getMessage";

    public static final String FUNC_MESSAGE = "message";

    public static final String FUNC_SETMESSAGE = "setMessage";

    public static final Event MESSAGECHANGED_EVENT = new Event("MessageChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected LocalTestContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LocalTestContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LocalTestContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LocalTestContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<MessageChangedEventResponse> getMessageChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MESSAGECHANGED_EVENT, transactionReceipt);
        ArrayList<MessageChangedEventResponse> responses = new ArrayList<MessageChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MessageChangedEventResponse typedResponse = new MessageChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newMessage = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MessageChangedEventResponse> messageChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MessageChangedEventResponse>() {
            @Override
            public MessageChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MESSAGECHANGED_EVENT, log);
                MessageChangedEventResponse typedResponse = new MessageChangedEventResponse();
                typedResponse.log = log;
                typedResponse.newMessage = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MessageChangedEventResponse> messageChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MESSAGECHANGED_EVENT));
        return messageChangedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> getMessage() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> message() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setMessage(String _newmessage) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_newmessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static LocalTestContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LocalTestContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LocalTestContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LocalTestContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LocalTestContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LocalTestContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LocalTestContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LocalTestContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LocalTestContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(LocalTestContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<LocalTestContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LocalTestContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<LocalTestContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(LocalTestContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<LocalTestContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LocalTestContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class MessageChangedEventResponse extends BaseEventResponse {
        public String newMessage;
    }
}
