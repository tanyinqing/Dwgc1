package com.yzkj.dwgc1;

import java.util.Calendar;

import com.yzkj.application.XlwApplication;
import com.yzkj.ceshi.PromptManager;
import com.yzkj.db.dbadapter;
import com.yzkj.model.BasicInformation;
import com.yzkj.utils.PreferenceUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/*
 * 保存输入的数据的到数据库或配置信息  save_basic(int i)
 * 这个方法就是初始化时让自己填上数据  init
 */
public class BasicInformation_power extends BaseActivity implements
		View.OnTouchListener {

	//旗帜变量  标明是变电工程还是线路工程
	private int title;
	
	//工程规模显示的数据
		private TextView gongchengguime;

	// 从上一个页面传递过来的数据
	private String pass_id;
	private String pass_promject;
	private String pass_databass;

	// 需要输入时间的输入框
	private EditText et_basic2;
	private EditText et_basic3;
	private EditText et_basic4;
	private EditText et_basic5;
	private EditText et_basic9;

	// 初始化数据表的数据
	String name = "";
	String stage = "";
	String stagedata = "";
	String startdata = "";
	String commissioningdata = "";
	String buildname = "";
	String supervisorunit = "";
	String counstructedunit = "";
	String voltageclass = "";
	String projectscale = "";

	// 需要单独存储在配置文件里的参数
	String voltageclass1 = "";
	String projectscale1 = "";

	// 保存信息的工程阶段 判断的状态变量
	private int save_state;

	// 保存配置信息
	private SharedPreferences sp;
	private PreferenceUtil sp_util;
	//显示当前的工程名称
	private String promject;
	
	private InputMethodManager inputManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_basicinformation);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_basicinformation);

		PromptManager.showToastTest1(this,
				"BasicInformation_power 这个是 工程基本信息 页面");
		PromptManager
				.showDialogTest1(this,
						"BasicInformation_power 这个是 工程基本信息 页面     ");

		
		sp_util = new PreferenceUtil(BasicInformation_power.this,"config", MODE_PRIVATE);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		inputManager=(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);  
		// 从上一个页面传递过来的数据
		// Bundle bundle=this.getIntent().getExtras();

		// 获得从上一个页面获得的数据
		getData();

		// 初始化重现上一次的数据
		init();

		// 根据跳转的页面 变换标题
		selectTitle();
		gongchengguime=(TextView)findViewById(R.id.gongchengguime);
		et_basic2 = (EditText) this.findViewById(R.id.et_basic2);
		et_basic3 = (EditText) this.findViewById(R.id.et_basic3);
		et_basic4 = (EditText) this.findViewById(R.id.et_basic4);
		et_basic5 = (EditText) this.findViewById(R.id.et_basic5);
		et_basic9 = (EditText) this.findViewById(R.id.et_basic9);
		et_basic2.setOnTouchListener(this);
		et_basic3.setOnTouchListener(this);
		et_basic4.setOnTouchListener(this);
		et_basic5.setOnTouchListener(this);
		et_basic9.setOnTouchListener(this);
		if (title==1) {
			gongchengguime.setText("工程规模(MVA):");
		} else {
			gongchengguime.setText("工程规模(KM):");
		}
	}

	private void init() {		
		
		promject=sp.getString("promject", null);
		
		//将输入的数据作为对象保存在配置文件中  现在读出这个对象
		BasicInformation bin1=(BasicInformation) sp_util.readObject(promject);
				
		
		if (null!=bin1) {
			Log.d("查看保存的对象", promject+"  "+bin1.toString());
			//((EditText) findViewById(R.id.et_basic2)).setText(bin1.getStage());
			/* 这个签证阶段括起来   防止阶段保存错误 */ 
			 ((EditText) findViewById(R.id.et_basic1)).setText(bin1.getName());  
			
			//((EditText) findViewById(R.id.et_basic3)).setText(bin1.getStagedata());
			((EditText) findViewById(R.id.et_basic4)).setText(bin1.getStartdata());
			((EditText) findViewById(R.id.et_basic5)).setText(bin1.getCommissioningdata());
			((EditText) findViewById(R.id.et_basic6)).setText(bin1.getBuildname());
			((EditText) findViewById(R.id.et_basic7)).setText(bin1.getSupervisorunit());
			((EditText) findViewById(R.id.et_basic8)).setText(bin1.getCounstructedunit());
			((EditText) findViewById(R.id.et_basic9)).setText(bin1.getVoltageclass1());
			((EditText) findViewById(R.id.et_basic10)).setText(bin1.getProjectscale1());
		} 
		/* 这个签证阶段括起来   防止阶段保存错误 */
		/*
		 * else {
			((EditText) findViewById(R.id.et_basic1)).setText(sp.getString("name2",
					null));
			 
			 ((EditText) findViewById(R.id.et_basic2)).setText(sp.getString("stage2",
					null));  
			
			((EditText) findViewById(R.id.et_basic3)).setText(sp.getString(
					"stagedata2", null));
			((EditText) findViewById(R.id.et_basic4)).setText(sp.getString(
					"startdata2", null));
			((EditText) findViewById(R.id.et_basic5)).setText(sp.getString(
					"commissioningdata2", null));
			((EditText) findViewById(R.id.et_basic6)).setText(sp.getString(
					"buildname2", null));
			((EditText) findViewById(R.id.et_basic7)).setText(sp.getString(
					"supervisorunit2", null));
			((EditText) findViewById(R.id.et_basic8)).setText(sp.getString(
					"counstructedunit2", null));
			((EditText) findViewById(R.id.et_basic9)).setText(sp.getString(
					"voltageclass2", null));
			((EditText) findViewById(R.id.et_basic10)).setText(sp.getString(
					"projectscale2", null));
		}
		 */
		
		
	}

	// 根据跳转的页面 变换标题
	private void selectTitle() {
		switch (title) {

		case 1:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_basicinformation);
			break;
		case 2:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_basicinformation2);
			break;
		default:
			break;
		}
	}

	// 获得从上一个页面获得的数据
	private void getData() {
		Bundle bundle = this.getIntent().getExtras();
		pass_promject = bundle.getString("promject");
		pass_id = bundle.getString("id");
		pass_databass = bundle.getString("databass");
		title = bundle.getInt("title");
	}

	// 返回上一页的函数
	public void goToNect(View view) {
		switch (view.getId()) {
		case R.id.stage2_return:
			if (title == 1) {
				Intent intent_return = new Intent(this, Stage1.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", pass_id);
				bundle.putString("promject", pass_promject);
				bundle.putString("databass", pass_databass);
				intent_return.putExtras(bundle);
				startActivity(intent_return);
				finish();
			} else {
				Intent intent_return = new Intent(this, Stage2.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", pass_id);
				bundle.putString("promject", pass_promject);
				bundle.putString("databass", pass_databass);
				intent_return.putExtras(bundle);
				startActivity(intent_return);
				finish();
			}

			break;
		case R.id.check_title_return2:

			XlwApplication.getInstance().exit();
			break;
		case R.id.delete_yonghuming:
			et_basic4.setText("");
			
			et_basic4.setFocusable(true);  
			et_basic4.setFocusableInTouchMode(true);  
			et_basic4.requestFocus();  
		
			inputManager.showSoftInput(et_basic4, 0);  
            break;
		case R.id.delete_yonghuming1:
			et_basic5.setText("");
			
			et_basic5.setFocusable(true);  
			et_basic5.setFocusableInTouchMode(true);  
			et_basic5.requestFocus();  
		
			inputManager.showSoftInput(et_basic5, 0);  
            break;
		default:
			break;
		}

	}

	// 让客户的其它选项做成智能化选择
	public void smart_select(int i) {
		switch (i) {
		case 1:
			if (title == 1) {
				final String[] mItems = { "工程开工", "基础完成", "土建交安", "安装完成",
						"工程投产" };
				Dialog dialog = null;
				ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(
						this, R.style.dialog);
				Builder builder = new AlertDialog.Builder(contextThemeWrapper);

				//builder.setTitle("请选择签证阶段!");
				
				View view1 = View.inflate(BasicInformation_power.this, R.layout.dialog_title2, null);
				TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
				tv.setText("请选择签证阶段!");
				builder.setCustomTitle(view1);

				builder.setItems(mItems, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Editor editor = sp.edit();
						switch (which) {
						case 0:
							et_basic2.setText("工程开工");
							et_basic2.requestFocus();
							// save_state=1;

							editor.putInt("save_state", 1);
							editor.commit();
							break;
						case 1:
							et_basic2.setText("基础完成");
							et_basic2.requestFocus();
							// save_state=2;
							editor = sp.edit();
							editor.putInt("save_state", 2);
							editor.commit();
							break;
						case 2:
							et_basic2.setText("土建交安");
							et_basic2.requestFocus();
							// save_state=3;
							editor = sp.edit();
							editor.putInt("save_state", 3);
							editor.commit();
							break;
						case 3:
							et_basic2.setText("安装完成");
							et_basic2.requestFocus();
							// save_state=4;
							editor = sp.edit();
							editor.putInt("save_state", 4);
							editor.commit();
							break;
						case 4:
							et_basic2.setText("工程投产");
							et_basic2.requestFocus();
							// save_state=5;
							editor = sp.edit();
							editor.putInt("save_state",5);
							editor.commit();
							break;

						default:
							break;
						}
					}
				}).show();
			} else { // 35kV及以上

				final String[] mItems = { "工程开工", "基础完成", "工程投产" };
				Dialog dialog = null;
				ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(
						this, R.style.dialog);
				Builder builder = new AlertDialog.Builder(contextThemeWrapper);

				//builder.setTitle("请选择签证阶段!");
				
				View view1 = View.inflate(BasicInformation_power.this, R.layout.dialog_title2, null);
				TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
				tv.setText("请选择签证阶段!");
				builder.setCustomTitle(view1);

				builder.setItems(mItems, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Editor editor = sp.edit();
						switch (which) {
						case 0:
							et_basic2.setText("工程开工");
							et_basic2.requestFocus();
							// save_state=6;
							editor = sp.edit();
							editor.putInt("save_state", 6);
							editor.commit();
							break;
						case 1:
							et_basic2.setText("基础完成");
							et_basic2.requestFocus();
							// save_state=7;
							editor = sp.edit();
							editor.putInt("save_state", 7);
							editor.commit();
							break;
						case 2:
							et_basic2.setText("工程投产");
							et_basic2.requestFocus();
							// save_state=8;
							editor = sp.edit();
							editor.putInt("save_state", 8);
							editor.commit();
							break;

						default:
							break;
						}
					}
				}).show();
			}

			break;
		case 2:
			final String[] mItems = { "35kV", "110kV", "220kV", "500kV" };
			Dialog dialog = null;
			ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(
					this, R.style.dialog);
			Builder builder = new AlertDialog.Builder(contextThemeWrapper);

			//builder.setTitle("请选择电压等级:");
			
			
			View view1 = View.inflate(BasicInformation_power.this, R.layout.dialog_title2, null);
			TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
			tv.setText("请选择电压等级:");
			builder.setCustomTitle(view1);

			builder.setItems(mItems, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					switch (which) {
					case 0:
						et_basic9.setText("35kV");
						et_basic9.requestFocus();
						break;
					case 1:
						et_basic9.setText("110kV");
						et_basic9.requestFocus();
						break;
					case 2:
						et_basic9.setText("220kV");
						et_basic9.requestFocus();
						break;
					case 3:
						et_basic9.setText("500kV");
						et_basic9.requestFocus();
						break;

					default:
						break;
					}
				}
			}).show();

		default:
			break;
		}
	}

	// 保存时弹出保存的阶段的弹出对话框
	public void save(View view) {	
		
		save_state=sp.getInt("save_state", 1);
		
		PromptManager.showToastTest1(this, String.valueOf(save_state));

		switch (title) {
		case 1:
			save_basic(1);
			save_basic(2);
			save_basic(3);
			save_basic(4);
			save_basic(5);
			break;
		case 2:
			save_basic(6);
			save_basic(7);
			save_basic(8);
			break;
			/*
			 * case 3:
			save_basic(3);
			break;
		case 4:
			save_basic(4);
			break;
		case 5:
			save_basic(5);
			break;
		case 6:
			save_basic(6);
			break;
		case 7:
			save_basic(7);
			break;
		case 8:
			save_basic(8);
			break;
			 */
		

		default:
			break;
		}

		

	}

	// 保存输入的数据  把数据分别存入配置信息或数据库里
	public void save_basic(int i) {
		//stage = ((EditText) findViewById(R.id.et_basic2)).getText().toString();		
		name = ((EditText) findViewById(R.id.et_basic1)).getText().toString();		
		stagedata = ((EditText) findViewById(R.id.et_basic3)).getText()
				.toString();
		startdata = ((EditText) findViewById(R.id.et_basic4)).getText()
				.toString();
		commissioningdata = ((EditText) findViewById(R.id.et_basic5)).getText()
				.toString();
		buildname = ((EditText) findViewById(R.id.et_basic6)).getText()
				.toString();
		supervisorunit = ((EditText) findViewById(R.id.et_basic7)).getText()
				.toString();
		counstructedunit = ((EditText) findViewById(R.id.et_basic8)).getText()
				.toString();
		voltageclass = "("
				+ ((EditText) findViewById(R.id.et_basic9)).getText()
						.toString() + ")千伏";
		projectscale = "("
				+ ((EditText) findViewById(R.id.et_basic10)).getText()
						.toString() + ")万千伏安/公里";
		voltageclass1 = ((EditText) findViewById(R.id.et_basic9)).getText()
				.toString();
		projectscale1 = ((EditText) findViewById(R.id.et_basic10)).getText()
				.toString();
		
		//将输入的数据作为对象保存在配置文件中
		BasicInformation bin=new BasicInformation();
		//bin.setStage(stage);
		bin.setName(name);
		//bin.setStagedata(stagedata);
		bin.setStartdata(startdata);
		bin.setCommissioningdata(commissioningdata);
		bin.setBuildname(buildname);
		bin.setSupervisorunit(supervisorunit);
		bin.setCounstructedunit(counstructedunit);				
		bin.setVoltageclass1(voltageclass1);
		bin.setProjectscale1(projectscale1);
		
		//sp_util.putSetting("tanyinqing", "tanyinqing");
		Log.d("保存的对象", promject+"  "+bin.toString());
		sp_util.putSetting(promject, bin);
		/*
		 * Editor editor = sp.edit();
		if (title==1) {
			editor.putString("name1", name);
			editor.putString("stage1", stage);
			editor.putString("stagedata1", stagedata);
			editor.putString("startdata1", startdata);
			editor.putString("commissioningdata1", commissioningdata);
			editor.putString("buildname1", buildname);
			editor.putString("supervisorunit1", supervisorunit);
			editor.putString("counstructedunit1", counstructedunit);		
			editor.putString("voltageclass1", voltageclass1);
			editor.putString("projectscale1", projectscale1);
		} else {
			editor.putString("name2", name);
			editor.putString("stage2", stage);
			editor.putString("stagedata2", stagedata);
			editor.putString("startdata2", startdata);
			editor.putString("commissioningdata2", commissioningdata);
			editor.putString("buildname2", buildname);
			editor.putString("supervisorunit2", supervisorunit);
			editor.putString("counstructedunit2", counstructedunit);		
			editor.putString("voltageclass2", voltageclass1);
			editor.putString("projectscale2", projectscale1);
		}
		
		
		editor.commit();// 提交
		 */
		
		
		dbadapter db = new dbadapter(pass_databass, this, "record", null, null);
		db.open();
		// stage,
		Boolean a = db.updateContactcause(i, name, stagedata, startdata,
				commissioningdata, buildname, supervisorunit, counstructedunit,
				voltageclass, projectscale);
		db.close();
		Log.d("保存个人档案信息", String.valueOf(a));

		

		if (a) {
			if (i==5||i==8) {
				Toast.makeText(this, "保存成功", 0).show();
			}			
		} else {			
				Toast.makeText(this, "保存失败", 0).show();			
			
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			ContextThemeWrapper contextThemeWrapper =

					new ContextThemeWrapper(this, R.style.dialog);
			AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
			View view = View.inflate(this, R.layout.common_datetime, null);
			final DatePicker datePicker = (DatePicker) view
					.findViewById(R.id.datepicker);
			//final TimePicker timePicker = (android.widget.TimePicker) view
					//.findViewById(R.id.timepicker);
			builder.setView(view);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH), null);
			//timePicker.setIs24HourView(true);
			//timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
			//timePicker.setCurrentMinute(Calendar.MINUTE);
			if (v.getId() == R.id.et_basic3) {
				final int inType = et_basic3.getInputType();
				et_basic3.setInputType(InputType.TYPE_NULL);
				et_basic3.onTouchEvent(event);
				et_basic3.setInputType(inType);
				et_basic3.setSelection(et_basic3.getText().length());
				//builder.setTitle("选取签证时间");
				
				View view1 = View.inflate(BasicInformation_power.this, R.layout.dialog_title2, null);
				TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
				tv.setText("选取签证时间");
				builder.setCustomTitle(view1);
				
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								//sb.append("  ");
								//sb.append(timePicker.getCurrentHour())
										//.append(":")
										//.append(timePicker.getCurrentMinute());
								et_basic3.setText(sb);
								et_basic3.requestFocus();
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
			} else if (v.getId() == R.id.et_basic4) {
				int inType = et_basic4.getInputType();
				et_basic4.setInputType(InputType.TYPE_NULL);
				et_basic4.onTouchEvent(event);
				et_basic4.setInputType(inType);
				et_basic4.setSelection(et_basic4.getText().length());
				//builder.setTitle("选取开工时间");
				
				View view1 = View.inflate(BasicInformation_power.this, R.layout.dialog_title2, null);
				TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
				tv.setText("选取实际开工时间");
				builder.setCustomTitle(view1);
				
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								//sb.append("  ");
								//sb.append(timePicker.getCurrentHour())
										//.append(":")
										//.append(timePicker.getCurrentMinute());
								et_basic4.setText(sb);
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
			} else if (v.getId() == R.id.et_basic5) {
				int inType = et_basic4.getInputType();
				et_basic5.setInputType(InputType.TYPE_NULL);
				et_basic5.onTouchEvent(event);
				et_basic5.setInputType(inType);
				et_basic5.setSelection(et_basic5.getText().length());
				//builder.setTitle("选取投产时间");
				
				View view1 = View.inflate(BasicInformation_power.this, R.layout.dialog_title2, null);
				TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
				tv.setText("选取预计投产时间");
				builder.setCustomTitle(view1);
				
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								//sb.append("  ");
								//sb.append(timePicker.getCurrentHour())
									//	.append(":")
										//.append(timePicker.getCurrentMinute());
								et_basic5.setText(sb);
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
			} else if (v.getId() == R.id.et_basic2) {
				smart_select(1);
			} else if (v.getId() == R.id.et_basic9) {
				smart_select(2);
			}

			// Dialog dialog = builder.create();
			// dialog.show();
		}
		return true;
	}
	
	
	 //让程序比较友好的呈现  在按一次退出
	 private long exitTime = 0;
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	         if((System.currentTimeMillis()-exitTime) > 2000){  
	             Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	             exitTime = System.currentTimeMillis();   
	         } else {
	             finish();
	             System.exit(0);
	         }
	         return true;   
	     }
	     return super.onKeyDown(keyCode, event);
	 }

}
