package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        if (state.isPreview) "Profile Preview" else "Edit Profile",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (state.isPreview) {
                ProfilePreview(state = state, onBack = { viewModel.backToEdit() })
            } else {
                ProfileForm(state = state, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ProfileForm(state: ProfileUiState, viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileInputField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = "Full Name",
            icon = Icons.Default.Person
        )
        ProfileInputField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = "Email Address",
            icon = Icons.Default.Email
        )
        ProfileInputField(
            value = state.contactNumber,
            onValueChange = viewModel::onContactChange,
            label = "Contact Number",
            icon = Icons.Default.Phone
        )
        ProfileInputField(
            value = state.address,
            onValueChange = viewModel::onAddressChange,
            label = "Home Address",
            icon = Icons.Default.Home
        )
        ProfileInputField(
            value = state.username,
            onValueChange = viewModel::onUsernameChange,
            label = "Username",
            icon = Icons.Default.Badge
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text("Professional Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.newSkill,
                onValueChange = viewModel::onNewSkillChange,
                label = { Text("Add Skill") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            FilledIconButton(
                onClick = viewModel::addSkill,
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add skill")
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.skills.forEach { skill ->
                InputChip(
                    selected = false,
                    onClick = { },
                    label = { Text(skill) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove",
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { viewModel.removeSkill(skill) },
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = viewModel::showPreview,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Visibility, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("View Profile Preview", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfilePreview(state: ProfileUiState, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = state.name.ifBlank { "N/A" },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "@${state.username.ifBlank { "username" }}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PreviewRow(icon = Icons.Default.Email, label = "Email", value = state.email)
                PreviewRow(icon = Icons.Default.Phone, label = "Contact", value = state.contactNumber)
                PreviewRow(icon = Icons.Default.Home, label = "Address", value = state.address)
            }
        }

        Spacer(Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                "Skills & Expertise",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            if (state.skills.isEmpty()) {
                Text("No skills added yet.", style = MaterialTheme.typography.bodyMedium)
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.skills.forEach { skill ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(skill) }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(40.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Back to Editing", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun PreviewRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Text(value.ifBlank { "Not provided" }, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        content = { content() }
    )
}
