package com.xht.android.managerhelp.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONObject;

import java.util.LinkedHashMap;

public class VolleyHelpApi extends BaseApi{
	private static final String TAG = "VolleyHelpApi";
	
	private static VolleyHelpApi sVolleyHelpApi;
	public static synchronized VolleyHelpApi getInstance() {
		if (sVolleyHelpApi == null) {
			sVolleyHelpApi = new VolleyHelpApi();
		}
		return sVolleyHelpApi;
	}
	private VolleyHelpApi() {}
	/**
	 * 检查版本更新 getCheckVersion
	 */
	public void getCheckVersion(final APIListener apiListener) {
		String urlString = MakeURL(CHECK_VERSION_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG,"-----------"+ response.toString());
					JSONObject jsonObject = response.optJSONObject("entity");
					apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，请稍后再试");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//
	public void getMainData(final int uid, final APIListener apiListener) {
		String urlS = MakeURL(MAIN_URL, new LinkedHashMap<String, Object>() {

		});
		LogHelper.i(TAG, urlS);
		JsonObjectRequest req = new JsonObjectRequest(urlS, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, "-------"+response.toString());//{"message":"系统错误","result":"error","entity":null,"code":"0"}
					apiListener.onResult(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}
	private boolean isResponseError(JSONObject jb){
		String errorCode = jb.optString("code","0");
		if(errorCode.equals("1")){
			return false;
		}		
		return true;
	}
	/**
	 * 登录提交
	 */
	public void postLogin(JSONObject jsonObject, final APIListener apiListener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					JSONObject jsonObject = response.optJSONObject("entity");
					LogHelper.i(TAG,"------"+response.toString());
					apiListener.onResult(jsonObject);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙,请稍后再试...");
			}
		}) {

		};
		App.getInstance().addToRequestQueue(req, TAG);
	}



	/**
	 * 修改账户提交确认
	 */
	public void postManagerData(JSONObject jsonObject, final APIListener apiListener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, MANAGER_ZH_URL, jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					JSONObject jsonObject = response.optJSONObject("entity");
					LogHelper.i(TAG,"----entity--"+response.toString());
					apiListener.onResult(jsonObject);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙,请稍后再试...");
			}
		}) {

		};
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 修改密码提交确认
	 */
	public void postMiMaData(JSONObject jsonObject, final APIListener apiListener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, MANAGER_MIMA_URL, jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					JSONObject jsonObject = response.optJSONObject("entity");
					LogHelper.i(TAG,"----entity--"+response.toString());
					apiListener.onResult(jsonObject);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙,请稍后再试...");
			}
		}) {

		};
		App.getInstance().addToRequestQueue(req, TAG);
	}


	
	public static String MakeURL(String p_url, LinkedHashMap<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
		}
		String temStr = url.toString().replace("?&", "?");
		
		return temStr;
	}




	/**
	 * 访问客户通讯录数据
	 * @param apiListener
	 */
	public void getTXLData( final APIListener apiListener) {
		String urlString = MakeURL(CONTACTSPOST_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}
	/**
	 * 访问内部通讯录数据
	 * @param apiListener
	 */
	public void getTXLInsideData( final APIListener apiListener) {
		String urlString = MakeURL(CONTACTS_GONGSI_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * 访问订单量（本日）
	 * @param apiListener
	 */
	public void getNewAddCustomerDay( final APIListener apiListener) {
		String urlString = MakeURL(ORDER_DAY_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * 访问订单量（本周）
	 * @param apiListener
	 */
	public void getNewAddCustomerWeek( final APIListener apiListener) {
		String urlString = MakeURL(ORDER_WEEk_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * 访问订单量（本月）
	 * @param apiListener
	 */
	public void getNewAddCustomerMonth( final APIListener apiListener) {
		String urlString = MakeURL(ORDER_MONTH_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 本日收款量
	 * @param apiListener
     */
	public void getMoneyDay( final APIListener apiListener) {
		String urlString = MakeURL(MONEY_DAY_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * b本周收款
	 * @param apiListener
     */
	public void getMoneyWeek(final APIListener apiListener) {
		String urlString = MakeURL(MONEY_WEEk_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * 本月收款量
	 * @param apiListener
     */
	public void getMoneyMonth( final APIListener apiListener) {
		String urlString = MakeURL(MONEY_MONTH_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//本日步骤量
	public void getStepNumberDay(final APIListener apiListener) {
		String urlString = MakeURL(STEP_DAY_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//本周步骤量
	public void getStepNumberWeek(final APIListener apiListener) {
		String urlString = MakeURL(STEP_WEEk_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//本月骤量
	public void getStepNumberMonth(final APIListener apiListener) {
		String urlString = MakeURL(STEP_MONTH_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//未处理本周
	public void getLowDatasWeek(final APIListener apiListener) {
		String urlString = MakeURL(WARNING_WEEk_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//未处理本月
	public void getLowDatasMonth(final APIListener apiListener) {
		String urlString = MakeURL(WARNING_MONTH_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//未处理本日
	public void getLowDatasDay(final APIListener apiListener) {
		String urlString = MakeURL(WARNING_DAY_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	//已处理
	public void getLowDoneDatas(final APIListener apiListener) {
		String urlString = MakeURL(WARNING_NONE_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//点击确认提交已处理
	public void postSureMethod(JSONObject object, final APIListener apiListener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, WARNING_SURE_URL, object, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					LogHelper.i(TAG,"------"+response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙,请稍后再试...");
			}
		}) {

		};
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//本日
	public void getPerformanceDay(final APIListener apiListener) {
		String urlString = MakeURL(PERFORM_DAY_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//本月
	public void getPerformanceMonth(final APIListener apiListener) {
		String urlString = MakeURL(PERFORM_MONTH_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	//本周
	public void getPerformanceWeek(final APIListener apiListener) {
		String urlString = MakeURL(PERFORM_WEEK_URL, new LinkedHashMap<String, Object>() {{
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 获取所有的客户公司信息
	 */
	public void getDatasFromCustomer(final APIListener apiListener) {

		String strurl=MakeURL(GET_MYCUSTOMER_URL,new LinkedHashMap<String, Object>(){{

		}});

		JsonObjectRequest req = new JsonObjectRequest(strurl, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());

					LogHelper.i(TAG, "------entity--" + response.toString());
					apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	public void postSaveCompanyName(JSONObject obj, APIListener apiListener) {



	}

	/**
	 * 获取公司信息和人员信息
	 * @param companyid
	 * @param apiListener
	 */
	public void getCompamyDatas(final String companyid, final APIListener apiListener) {
		String urlString = MakeURL(COMPLETE_NAME_URL, new LinkedHashMap<String, Object>() {{
			put("companyid", companyid);
		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				LogHelper.i(TAG, "----的所有信息--" + response.toString());
				apiListener.onResult(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 获取客户订单数据
	 * @param companyid
	 * @param apiListener
     */
	public void getOrderListDatas(final String companyid, final APIListener apiListener) {
//{"message":"没有订单","result":"error","entity":null,"code":"0"}
		String strUrl=MakeURL(CUSTOMER_ORDER_URL,new LinkedHashMap<String, Object>(){{
			put("companyid",companyid);
		}});

		JsonObjectRequest reg=new JsonObjectRequest(strUrl, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//{"message":"没有订单","result":"error","entity":null,"code":"0"}
				LogHelper.i(TAG,"--------"+response.toString());
				apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});

		App.getInstance().addToRequestQueue(reg, TAG);

	}

	/**
	 * 获取订单详细信息
	 * @param orderId
	 * @param apiListener
     */
	public void getOrderDetials(final String orderId,final String orderType, final APIListener apiListener) {
		String strUrl=MakeURL(DETIALS_ORDER_URL,new LinkedHashMap<String, Object>(){{
			put("orderId",orderId);//?orderId=10&orderType=10
			put("orderType",orderType);//?orderId=10&orderType=10
		}});

		JsonObjectRequest reg=new JsonObjectRequest(strUrl, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//{"message":"没有订单","result":"error","entity":null,"code":"0"}
				apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});

		App.getInstance().addToRequestQueue(reg, TAG);

	}

	/**
	 * 报税记录
	 * @param companyId
	 * @param year
     */
	public void getDatasTax(final String companyId, final String year, final APIListener apiListener) {
		String strUrl=MakeURL(DETIALS_TAX_URL,new LinkedHashMap<String, Object>(){{
			put("companyId",companyId);
			put("year",year);
		}});

		JsonObjectRequest reg=new JsonObjectRequest(strUrl, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//{"message":"没有订单","result":"error","entity":null,"code":"0"}
				LogHelper.i(TAG,"--------"+response.toString());
				apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});

		App.getInstance().addToRequestQueue(reg, TAG);

	}


	/**
	 * 获取通讯录
	 * @param companyid
	 * @param apiListener
     */
	public void getContactsManager(final String companyid,  final APIListener apiListener) {
		String strUrl=MakeURL(CONTACT_URL,new LinkedHashMap<String, Object>(){{
			put("companyid",companyid);//?companyid=16
		}});

		JsonObjectRequest reg=new JsonObjectRequest(strUrl, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				//{"message":"没有订单","result":"error","entity":null,"code":"0"}
				LogHelper.i(TAG,"--------"+response.toString());
				apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});

		App.getInstance().addToRequestQueue(reg, TAG);

	}

	/**
	 * 添加成员
	 */
	public void postAddcontacts(JSONObject object, final APIListener apiListener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, ADD_CONTACTS_URL, object, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					LogHelper.i(TAG,"------"+response.toString());
					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙,请稍后再试...");
			}
		}) {

		};
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 获取图片
	 */
	public void getPictureDatas(final String orderId,  final APIListener apiListener) {
		String strUrl=MakeURL(ORDER_PIC_URL,new LinkedHashMap<String, Object>(){{
			put("orderId",orderId);//?companyid=16
		}});

		JsonObjectRequest reg=new JsonObjectRequest(strUrl, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG,"--------"+response.toString());
				apiListener.onResult(response);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});

		App.getInstance().addToRequestQueue(reg, TAG);

	}
}


	/**
	 * 首页访问数据
	 * @param apiListener
     *//*
	public void getMainData(final int uid, final APIListener apiListener) {
		String urlString = MakeURL(URL_Main, new LinkedHashMap<String, Object>() {{
			put("userid", uid);

		}});
		LogHelper.i(TAG,"---------getMainData-uid-----"+uid);
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());

					LogHelper.i(TAG, "------===首页的所有信息--" + response.toString());
					apiListener.onResult(response);


			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}*/



