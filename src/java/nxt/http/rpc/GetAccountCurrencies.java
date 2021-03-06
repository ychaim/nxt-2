package nxt.http.rpc;

import nxt.Account;
import nxt.MofoQueries;
import nxt.Transaction;
import nxt.db.DbIterator;
import nxt.http.ParameterException;
import nxt.http.websocket.JSONData;
import nxt.http.websocket.RPCCall;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

public class GetAccountCurrencies extends RPCCall {
    
    public static RPCCall instance = new GetAccountCurrencies("getAccountCurrencies");
    static int COUNT = 10;

    public GetAccountCurrencies(String identifier) {
        super(identifier);
    }
  
    @SuppressWarnings("unchecked")
    @Override
    public JSONStreamAware call(JSONObject arguments) throws ParameterException {
        Account account = ParameterParser.getAccount(arguments);
        int firstIndex = ParameterParser.getFirstIndex(arguments);
        int lastIndex = ParameterParser.getLastIndex(arguments);
        
        JSONArray response = new JSONArray();
        try (
            DbIterator<Account.AccountCurrency> iterator = account.getCurrencies(firstIndex, lastIndex);
        ) {  
            while (iterator.hasNext()) {
                response.add(JSONData.accountCurrency(iterator.next(), true));
            }
        }        
        
        return response;
    }  

}
