package com.ecommerce.service.impl;

import com.ecommerce.common.base.CommonPage;
import com.ecommerce.dao.WtrWalletTransactionRecordMapper;
import com.ecommerce.pojo.WtrWalletTransactionRecord;
import com.ecommerce.pojo.WtrWalletTransactionRecordExample;
import com.ecommerce.service.WalletAdminService;
import com.ecommerce.vojo.WalletAdminVO;
import com.ecommerce.vojo.WalletPageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletAdminServiceImpl implements WalletAdminService {

    @Resource
    WtrWalletTransactionRecordMapper wtrWalletTransactionRecordMapper;

    @Override
    public CommonPage<WalletAdminVO> getAllFlow(WalletPageVO vo) {
        Page<WtrWalletTransactionRecord> flowPage = PageHelper.startPage(vo.getPageNum(), vo.getPageSize()).doSelectPage(() -> {
            WtrWalletTransactionRecordExample example = new WtrWalletTransactionRecordExample();
            example.createCriteria().andStatusEqualTo((byte)2);
            wtrWalletTransactionRecordMapper.selectByExample(example);
        });

        List<WalletAdminVO> flows = new ArrayList<>();
        for (WtrWalletTransactionRecord record:flowPage.getResult()) {
            WalletAdminVO flow = new WalletAdminVO();
            flow.setAccountName(record.getAccountName());
            flow.setAvailableMoney(record.getTransactionMoney());
            flow.setCreateTime(record.getCreateTime());
            flow.setStatus((byte)2);
            flow.setTransactionType(record.getTransactionType());
            flow.setTransactionNumber(record.getTransactionNumber());
            flows.add(flow);
        }
        return CommonPage.restPage(flows,flowPage);
    }
}
