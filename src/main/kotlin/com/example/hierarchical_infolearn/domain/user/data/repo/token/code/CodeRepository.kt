package com.example.hierarchical_infolearn.domain.user.data.repo.token.code

import com.example.hierarchical_infolearn.domain.user.data.entity.token.code.AuthCode
import org.springframework.data.repository.CrudRepository

interface CodeRepository: CrudRepository<AuthCode, String>