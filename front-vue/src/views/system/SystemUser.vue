<template>
  <el-row>
    <!-- <el-col :span="4">
      <el-input
        placeholder="输入关键字进行过滤"
        v-model="filterText">
      </el-input>
      <el-tree
        class="filter-tree"
        :data="orgData"
        :props="defaultProps"
        default-expand-all
        :expand-on-click-node="false"
        :filter-node-method="filterOrg"
        @node-click="orgNodeClick"
        :highlight-current="true"
        ref="orgQueryTree">
      </el-tree>
    </el-col> -->

    <el-col >
      <div>
        <el-card body-style="padding: 0">
          <el-form ref="userQueryForm" :model="userQueryForm" label-width="80px">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="用户名称" prop="username">
                  <el-input v-model="userQueryForm.username" placeholder="请输入用户名称"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="userQueryForm.phone" placeholder="请输入联系电话"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="用户邮箱" prop="email">
                  <el-input v-model="userQueryForm.email" placeholder="请输入用户邮箱"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="用户状态" prop="enabled">
                  <dict-select :dictValue.sync="userQueryForm.enabled"
                               groupCode="sysuser.enabled"
                               placeholder="请选择用户状态">
                  </dict-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="创建时间" prop="timeRange">
                  <el-date-picker
                    v-model="userQueryForm.timeRange"
                    type="daterange"
                    value-format="yyyy-MM-dd hh:mm:ss"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="6" padding="0 20px">
                  <el-button type="primary" size="small"
                             @click="submitQueryForm()" icon="el-icon-search">
                    查询</el-button>
                  <el-button type="primary" size="small" plain
                             @click="resetQueryForm('userQueryForm')" icon="el-icon-refresh">
                    重置</el-button>
              </el-col>
            </el-row>
          </el-form>
        </el-card>

        <el-card>
          <el-button type="primary" size="small" style="margin: 0 0 10px 20px"
                     icon="el-icon-plus" @click="handleAdd('新增用户')">新增</el-button>

          <el-table :data="tableData" border default-expand-all stripe style="width: 100%;margin-bottom: 20px;">
            <el-table-column prop="username" label="用户名称" width="100" align="center"/>
            <el-table-column  label="用户头像" width="100" align="center">
              <template slot-scope="scope">
                  <div>
                    <img :src="getUrl(scope.row.avator)" alt="" style="width: 100%;"/>
                  </div>
                  <el-upload
                    class="upload-demo"
                    :action="uploadUrl(scope.row.id)"
                    :headers="myHeaders"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    >
                    <el-button size="mini">更新图片</el-button>
                  </el-upload>
              </template>
            </el-table-column>
            <el-table-column prop="phone" label="联系电话" width="120" align="center"/>
            <el-table-column prop="email" label="用户邮箱" width="150" align="center"/>
            <el-table-column  label="创建时间" width="200" align="center" >
                <template slot-scope="scope">
                <div>{{formData(scope.row.createTime)}}</div>
            </template>
            </el-table-column>
           <el-table-column label="性别" width="50" align="center">
            <template slot-scope="scope">
                <div>{{changeSex(scope.row.sex) }}</div>
            </template>
           </el-table-column>
            <el-table-column label="生日" width="120" align="center">
            <template slot-scope="scope">
                <div>{{formData(scope.row.birth)}}</div>
            </template>
            </el-table-column>
            <el-table-column prop="location" label="用户地区" width="130" align="center"/>
            <el-table-column prop="introduction" label="用户介绍" width="130" align="center"/>
            <el-table-column label="用户详情" width="130" align="center">
              <template  slot-scope="scope">
                  <el-button  @click="getCenter(scope.row.id)">用户详情</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="enabled" label="用户状态" width="150" align="center">
              <template slot-scope="scope">
                <el-switch
                  active-text ="激活"
                  inactive-text = "禁用"
                  v-model="scope.row.enabled"
                  @change=changeEnabled(scope.$index,scope.row)
                >
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="300" align="center" fixed="right">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" icon="el-icon-edit" circle
                           @click="handleEdit(scope.$index, scope.row,'修改用户')"/>
                <el-button size="mini" type="danger" icon="el-icon-delete" circle
                           @click="handleDelete(scope.$index, scope.row)"/>
                <el-button size="mini" type="success"
                           @click="resetPwd(scope.$index, scope.row)">
                  重置密码
                </el-button>
                <el-button size="mini" type="success"
                           @click="assignRole(scope.$index, scope.row)">
                  分配角色
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            layout="total, sizes, prev, pager, next, jumper"
            :current-page="pagination.pageNum"
            :page-size="pagination.pageSize"
            :total="pagination.total"
            @size-change="handlePageSizeChange"
            @current-change="handlePageNumChange"
            background
            style="float: right;margin-bottom: 10px">
          </el-pagination>
        </el-card>



        <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible"
                   :before-close="beforeDialogClose">
          <el-alert
            :title="dialogAlertInfo"
            type="error" :closable="false">
          </el-alert>
          <el-form :model="dialogForm" ref="dialogForm"
                   :rules="dialogFormRules" label-width="80px">

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="用户名称" prop="username">
                  <el-input v-model="dialogForm.username" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>

              <!-- <el-col :span="12">
                <el-form-item label="用户组织" prop="orgId">
                    <el-tree-select
                      :elTreeProps="elTreeProps"
                      :elTreeData="orgData"
                      :defaultSelectedId="dialogForm.orgId"
                      :disabled="elTreeDisabled"
                      @handleTreeSelected="handleTreeSelected($event)"
                      @validateSelectTree="validateSelectTree">
                    </el-tree-select>
                </el-form-item>
              </el-col> -->
               <el-col :span="12">
                <el-form-item label="用户地区" prop="username">
                  <el-input v-model="dialogForm.location" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>          
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="用户邮箱" prop="email">
                  <el-input v-model="dialogForm.email"  label="角色描述"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="dialogForm.phone" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>

            </el-row>
             <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="用户性别" prop="sex">
                 <el-radio-group v-model="dialogForm.sex">
                       <el-radio  :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                 </el-radio-group>
                </el-form-item>
              </el-col>
              
              <el-col :span="12">
                <el-form-item label="用户生日" prop="birth">
                  <el-date-picker type="date" placeholder="选择日期" v-model="dialogForm.birth" style="width: 100%;"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="个人介绍" prop="phone">
                  <el-input type="textarea" placeholder="签名" v-model="dialogForm.introduction" autocomplete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="submitDialogForm()" size="mini" type="primary">确 定</el-button>
            <el-button @click="handleCloseDialog" size="mini">取 消</el-button>
          </div>
        </el-dialog>



        <el-dialog :title="roleDialogTitle" :visible.sync="roleDialogVisible">
          <el-button type="primary"
                     size="small"
                     style="float: right"
                     @click="saveUserRoles">
            保存角色
          </el-button>
          <el-transfer
            v-model="checkedRoleIds"
            :props="roleDataProp"
            :data="roleDatas"
            :titles="['备选角色', '已选角色']">
          </el-transfer>
        </el-dialog>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import {getUsers,updateUser,addUser,deleteUser,resetUserPwd,changeEnabled}
  from '@/api/system/sys_user'
  import {getCheckedRoles,saveCheckedUserRoles} from '@/api/system/sys_role'
  import {getOrgTree} from '@/api/system/sys_org'
  import axios from 'axios'
  import MixinCUD from '@/components/MixinCUD'
  import ElTreeSelect from "@/components/TreeSelect";
  import DictSelect from "@/components/DictSelect";
  import * as dateUtils from "@/api/data";
  var token = localStorage.getItem("JWTHeaderName");
  export default {
    name: "SystemUser",
    mixins: [MixinCUD],
    components:{ElTreeSelect,DictSelect},
    data() {
      return {
        tableData: [],
        myHeaders:{JWTHeaderName: token},
        queryFormRefName:"userQueryForm",
        userQueryForm:{
          avator: '',
          sex: '',
          introduction: '',
          birth: '',
          location: '',
          username: "",
          phone:"",
          enabled:"",
          email:"",
          orgId:null,
          timeRange: ["",""]
        },
        pagination:{
          pageNum: 1,
          pageSize: 20,
          total: null
        },
        filterText: '',
        orgData:[],
        defaultProps: {
          children: 'children',
          label: 'orgName'
        },
        dialogFormVisible: false,
        dialogTitle:"",
        dialogRefName:"dialogForm",
        dialogForm: {
          id: null,
          username: "",
          phone:"",
          email:"",
          location:"",
          sex:"",
          introduction:"",
          birth: '',
          orgId:null,
        },
        dialogFormRules: {
          username: [
            {required: true, message: '请输入用户名称', trigger: 'blur'},
          ],
          orgId: [
            {required: true, message: '请选择用户组织', trigger: 'blur'},
          ],
          email: [
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
          ],
          phone: [
            {pattern: /^1[34578]\d{9}$/, message: '目前只支持中国大陆的手机号码', trigger: 'blur'}
          ],
        },
        elTreeDisabled:false,
        elTreeProps:{         // el-tree-select配置项（必选）
          value: 'id',
          label: 'orgName',
          children: 'children',
        },
        handlingUserId:null,
        roleDialogTitle:"",
        roleDialogVisible: false,
        checkedRoleIds:[],
        roleDataProp:{
          key: 'id',
          label: 'roleName'
        },
        roleDatas:[]
      }
    },
    watch: {
      filterText(val) {
        this.$refs.orgQueryTree.filter(val);
      }
    },
    computed:{
      dialogAlertInfo(){
        return "新增用户默认初始密码:" +
          this.$store.getters.getSysConfigItem("user.init.password") +
          ",会在用户登陆后提示用户自行修改。" +
          "如果您希望为用户修改密码，" +
          "请使用密码重置功能"
      }
    },
    methods: {
      //得到数据信息
      getData(){
        getUsers(this.userQueryForm,this.pagination)
          .then(res => {
            this.setData(res)
          })
      },
      updateData(){
        updateUser(this.dialogForm)
          .then(res => {
            this.$message({message: res.data, type: 'success'});
            this.submitQueryForm();//修改之后，重新查询table
            this.handleCloseDialog();
          })
      },

      // 增加数据
      addData(){
        addUser(this.dialogForm).then(res => {
          this.$message({message: res.data, type: 'success'});
          this.submitQueryForm();//新增之后，重新查询table
          this.handleCloseDialog();
        })
      },

      // 删除数据
      deleteData(row){
        this.$confirm("确定删除["+row.username+"]?")
          .then(_ => {
            deleteUser(row.id)
              .then(res => {
                this.submitQueryForm();//删除之后，重新查询table
                this.$message({message: res.data, type: 'success'});
              }).catch(err => {
              this.$message({message: err.message, type: 'error'});
            })
          });
      },

      changeEnabled(index,row){
        changeEnabled(row.id,row.enabled).then(res => {
          if(res.isok){
            this.$message({message: res.data, type: 'success'});
          }
        })
      },
     
     //个人中心
     getCenter(id){
         this.$router.push({path: '/home/center', query: {userId: id}})
     },
      // 分页相关的数据信息
      handlePageSizeChange(val){
        this.pagination.pageSize = val;
        this.submitQueryForm()
      },
      handlePageNumChange(val){
        this.pagination.pageNum = val;
        this.submitQueryForm()
      },
      filterOrg(value, data) {
        if (!value) return true;
        return data.orgName.indexOf(value) !== -1;
      },
      orgNodeClick(node){
        this.userQueryForm.orgId = node.id
        this.submitQueryForm()
      },
      handleTreeSelected(value){
        this.dialogForm.orgId = value
        this.$refs.dialogForm.validateField("orgId");
      },
      validateSelectTree(){
        this.$refs.dialogForm.validateField("orgId");
      },
      resetPwd(index,row){
        this.$confirm("确定重置密码为："
          + this.$store.getters.getSysConfigItem("user.init.password")
          + "么？")
        .then(_ => {
          resetUserPwd(row.id).then(res => {
            this.$message({message: res.data, type: 'success'})
          });
        });
      },

      // 用户的角色信息
      assignRole(index,row){
        this.roleDialogVisible = true
        this.roleDialogTitle = "用户" + row.username + "角色分配"
        this.handlingUserId = row.id
        getCheckedRoles(row.id).then(res => {
          this.roleDatas = res.data.roleDatas
          this.checkedRoleIds = res.data.checkedRoleIds
        })
      },

    // 保存用户的角色信息
      saveUserRoles(){
        if(this.checkedRoleIds.length > 0){
          saveCheckedUserRoles(this.handlingUserId,this.checkedRoleIds)
            .then(res => {
              this.$message({message: res.data, type: 'success'});
              this.roleDialogVisible = false
            })
        }else{
          this.$message.error("已选角色不能为空");
        }
      },
      setData(pageinfo) {
        if (pageinfo.isok) {
          this.tableData = pageinfo.data.records
          this.pagination.pageSize = pageinfo.data.size
          this.pagination.total = pageinfo.data.total
        }
      },
      setOrgData(orgTree){
        if (orgTree.isok) {
          this.orgData = orgTree.data
        }
      },

      // 日期的转换类
     formData (val) {
      let date = new Date(val);
      return dateUtils.formatDate(date,'yyyy-MM-dd');
    },
     
    // 用户的性别类的转换 
    changeSex (value) {
       return dateUtils.changeSex(value);
    },
  
     // 得到图片地址信息
    getUrl (url) {
      return `${this.$store.state.ONHOST}/${url}`
    },

    // 歌曲图片的上传
    uploadUrl (id) {
      return `${this.$store.state.HOST}/sysuser/updatePicture?id=${id}`
    },
    
    // 更新图片
    handleAvatarSuccess (res, file) {
      let _this = this
      console.log(res)
      if (res.isok) {
        _this.imageUrl = URL.createObjectURL(file.raw)
        this.$message({message: res.data, type: 'success'});
        this.submitQueryForm();
      } else {
          this.$message({message: res.data, type: 'error'});
      }
    },

    // 上传前的图片的校验
    beforeAvatarUpload (file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png')
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 20MB!')
      }
      return isJPG && isLt2M
    },

  
  },
  beforeRouteEnter(to, from, next) {
      axios.all([
        getUsers(
          {avator:'',sex:'',introduction:'',birth:'',location:'',username:"",phone:"",enabled:"",email:"",orgId:null,timeRange: ["",""]}
          ,{pageNum: 1, pageSize: 20,}
        )
       // getOrgTree({status:false,name:""})
        ])
      .then(axios.spread(function (res1) {
        // 两个请求都执行完成后，进入该函数
        next(vm => {vm.setData(res1);
        //console.log(res1);
       // vm.setOrgData(res2)
        })
      }));
    }
  }
</script>

<style scoped>
  .el-form {
    margin-top: 20px;
  }
  .el-tree--highlight-current /deep/ .el-tree-node.is-current > .el-tree-node__content {
    background-color: rgb(31, 158, 254);
    color: rgb(255, 255, 255);
  }
</style>